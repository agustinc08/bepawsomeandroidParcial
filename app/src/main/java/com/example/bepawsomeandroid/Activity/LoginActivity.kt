package com.example.bepawsomeandroid.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.example.bepawsomeandroid.Activity.MainActivity
import com.example.bepawsomeandroid.Models.Usuario
import com.example.bepawsomeandroid.R
import com.example.bepawsomeandroid.dao.roomDb
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

        // nombreUsuario: Tester
        // contrasenia: Tester

        loginBtn.setOnClickListener {
            if (nombreUsuario.text.toString() == "Tester" && contrasenia.text.toString() == "Tester") {
                Toast.makeText(this, "Logueado", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)

                // Co rutina necesaria para crear la base de datos ( obligatioria para trabajar dentro del onClickListener)

                CoroutineScope(Dispatchers.IO).launch {
                    // Inicializa la base de datos
                    val db = Room.databaseBuilder(applicationContext, roomDb::class.java, "mi-base-de-datos").build()
                    val usuarioDao = db.usuarioDao()

                    // Inserta datos en la base de datos
                    val nuevoUsuario = Usuario("", nombreUsuario.text.toString(), contrasenia.text.toString() )
                    usuarioDao.insertarUsuario(nuevoUsuario)
                }

                startActivity(intent)
            } else {
                Toast.makeText(this, "Acceso Denegado", Toast.LENGTH_SHORT).show()
            }
        }

        val clickListener = View.OnClickListener {
            Toast.makeText(this, "Usuario logueado", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        googleView.setOnClickListener(clickListener)
        fbView.setOnClickListener(clickListener)
        twitterView.setOnClickListener(clickListener)



    }
}




