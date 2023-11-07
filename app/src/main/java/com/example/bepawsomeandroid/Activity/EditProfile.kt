package com.example.bepawsomeandroid.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bepawsomeandroid.Models.User
import com.example.bepawsomeandroid.R
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.security.MessageDigest

class EditProfile : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextImageUrl: EditText
    private lateinit var saveButton: Button
    lateinit var nameUserCredential: String
    lateinit var mailUserCredential: String
    lateinit var imageUrlUserCredential: String
    lateinit var telefonoUserCredential: String
    lateinit var passwordUserCredential: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        var sharedPreferences: SharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE)
        val jsonObjectString = sharedPreferences.getString("UserLogueado", null)
        var jsonObject: JSONObject? = null
        if (jsonObjectString != null) {
            try {
                jsonObject = JSONObject(jsonObjectString)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        var gson = Gson()
        var userObject = gson.fromJson(jsonObject.toString(), User::class.java)

        nameUserCredential = userObject.name
        mailUserCredential = userObject.mail
        imageUrlUserCredential = userObject.imageUrl
        telefonoUserCredential = userObject.telefono
        passwordUserCredential = userObject.password

        val textViewUserName = findViewById<TextView>(R.id.textViewUserName)
        textViewUserName.text = nameUserCredential

        val imageViewUser = findViewById<ImageView>(R.id.imageViewUserProfile) // Asegúrate de tener un ImageView en tu layout
        if (imageUrlUserCredential.isNotEmpty()) {
            Picasso.get().load(imageUrlUserCredential).into(imageViewUser)
        }

        editTextName = findViewById(R.id.editTextName)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextImageUrl = findViewById(R.id.editTextImageUrl)
        saveButton = findViewById(R.id.btnSave)

        saveButton.setOnClickListener { onSaveClick() }
    }

    private fun onSaveClick() {
        val name = editTextName.text.toString()
        if (name.isNotBlank() && name != nameUserCredential ) {
            nameUserCredential = name
        }

        val email = editTextEmail.text.toString()
        if (email.isNotBlank() && email != mailUserCredential){
            mailUserCredential = email
        }

        val imageUrl = editTextImageUrl.text.toString()
        if (imageUrl.isNotBlank() && imageUrl != imageUrlUserCredential){
            imageUrlUserCredential = imageUrl
        }

        val phone = editTextPhone.text.toString()
        if (phone.isNotBlank() && phone!= telefonoUserCredential){
            telefonoUserCredential = phone
        }

        val password = editTextPassword.text.toString()
        if (password.isNotBlank() && password != passwordUserCredential) {
            passwordUserCredential = password
        }

        val sharedPreferences: SharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE)
        val gson = Gson()
        val userObject = User(nameUserCredential, mailUserCredential, passwordUserCredential, imageUrlUserCredential, telefonoUserCredential)
        val jsonUser = gson.toJson(userObject)

        with(sharedPreferences.edit()) {
            putString("UserLogueado", jsonUser)
            apply()
        }

        println(jsonUser)
        //println("Este es el nombre nuevo" + userObject.name)

        Toast.makeText(this, "${nameUserCredential} Datos Actualizados", Toast.LENGTH_SHORT).show()



        val profileData = JSONObject()
        profileData.put("name", nameUserCredential)
        profileData.put("password", passwordUserCredential)
        profileData.put("email", mailUserCredential)
        profileData.put("phone", telefonoUserCredential)
        profileData.put("imageUrl", imageUrlUserCredential)

        // Este JSON es el que enviaremos a Firebase
        val jsonString = profileData.toString()

        //println(jsonString)

        // Cierra la actividad actual
        finish()

        // Crea una nueva instancia de la actividad para recargarla
        val intent = Intent(this, EditProfile::class.java)
        startActivity(intent)

    }


}