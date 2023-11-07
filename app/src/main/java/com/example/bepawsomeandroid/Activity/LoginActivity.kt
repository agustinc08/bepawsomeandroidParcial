package com.example.bepawsomeandroid.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.bepawsomeandroid.Activity.MainActivity
import com.example.bepawsomeandroid.Models.ListaUsuariosPersistencia
import com.example.bepawsomeandroid.Models.User
import com.example.bepawsomeandroid.R
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val nombreUsuario: TextView = findViewById(R.id.nombreUsuario)
        val contrasenia: TextView = findViewById(R.id.contrasenia)
        val loginBtn: MaterialButton = findViewById(R.id.loginbtn)
        val googleView: ImageView= findViewById(R.id.googleView)
        val fbView: ImageView = findViewById(R.id.fbView)
        val twitterView: ImageView = findViewById(R.id.twitterView)
        val listaUsuarios = ListaUsuariosPersistencia()



        loginBtn.setOnClickListener {
            val nombreUsuarioInput = nombreUsuario.text.toString()
            val contraseniaInput = contrasenia.text.toString()

            val userEncontrado = listaUsuarios.encontrarUsuario(nombreUsuarioInput, contraseniaInput)

            if (userEncontrado != null) {
                Toast.makeText(this, "${userEncontrado.name} Logueado", Toast.LENGTH_SHORT).show()
                guardarPreferencias(userEncontrado)
                val intent = Intent(this, MainActivity::class.java)


                startActivity(intent)
            } else {
                Toast.makeText(this, "Acceso Denegado", Toast.LENGTH_SHORT).show()
            }
        }

        //Aca siempre loguea con Tester
        val clickListener = View.OnClickListener {
            val userTester = listaUsuarios.obtenerTester()
            guardarPreferencias(userTester)
            Toast.makeText(this, "${userTester.name} Logueado", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        googleView.setOnClickListener(clickListener)
        fbView.setOnClickListener(clickListener)
        twitterView.setOnClickListener(clickListener)


    }

   // fun cargarPreferencias(){
       //var preferencias:SharedPreferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE)
       // var nombreUsuario: String? = preferencias.getString("nombreUsuario","No Esta Cargado")
       // var mailUsuario: String? = preferencias.getString("mailUsuario","No Esta Cargado")
       // var userLogueado: Boolean? = preferencias.getBoolean("userLogueado",false)


  //  }

    fun guardarPreferencias(userEncontrado: User){
        var preferencias:SharedPreferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preferencias.edit()

        var nameUser = userEncontrado.name
        var mailUser = userEncontrado.mail
        var imageUrlUser = userEncontrado.imageUrl
        var telefonoUser = userEncontrado.telefono
        var passwordUser = userEncontrado.password

        editor.putString("nameUser" , nameUser)
        editor.putString("mailUser", mailUser)
        editor.putString("imageUrlUser", imageUrlUser)
        editor.putString("telefonoUser", telefonoUser)
        editor.putString("passwordUser", passwordUser)
        editor.putBoolean("userLogueado",true)
        editor.commit()
    }

}




