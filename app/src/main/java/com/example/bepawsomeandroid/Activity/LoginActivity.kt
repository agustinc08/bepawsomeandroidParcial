package com.example.bepawsomeandroid.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
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
        val googleButton: ImageButton = findViewById(R.id.googleButton)
        val fbButton: ImageButton = findViewById(R.id.fbButton)
        val twitterButton: ImageButton = findViewById(R.id.twitterButton)

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

        googleButton.setOnClickListener(clickListener)
        fbButton.setOnClickListener(clickListener)
        twitterButton.setOnClickListener(clickListener)
    }
}




