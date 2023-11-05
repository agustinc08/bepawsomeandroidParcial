package com.example.bepawsomeandroid.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.bepawsomeandroid.R
import com.google.android.material.button.MaterialButton

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




