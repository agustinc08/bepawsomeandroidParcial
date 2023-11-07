package com.example.bepawsomeandroid.Activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        var sharedPreferences: SharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE)
        val jsonObjectString = sharedPreferences.getString("UserLogueado", null)
        var jsonObject: JSONObject? = null
        if (jsonObjectString != null) {
            try {
                jsonObject = JSONObject(jsonObjectString)
                // Ahora tienes tu objeto JSON
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        var gson = Gson()
        var userObject = gson.fromJson(jsonObject.toString(), User::class.java)

        var nombreUser = userObject.name
        val textViewUserName = findViewById<TextView>(R.id.textViewUserName)
        textViewUserName.text = nombreUser

        var imagenProfileUser = userObject.imageUrl
        val imageViewUser = findViewById<ImageView>(R.id.imageViewUserProfile) // Asegúrate de tener un ImageView en tu layout
        if (imagenProfileUser.isNotEmpty()) {
            Picasso.get().load(imagenProfileUser).into(imageViewUser)
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
        val password = editTextPassword.text.toString()
        val email = editTextEmail.text.toString()
        val phone = editTextPhone.text.toString()
        val imageUrl = editTextImageUrl.text.toString()

        var passwordHash = ""

        if(!password.isBlank() || password != ""){ passwordHash = hashPassword(password)}

        val profileData = JSONObject()
        profileData.put("name", name)
        profileData.put("password", passwordHash)
        profileData.put("email", email)
        profileData.put("phone", phone)
        profileData.put("imageUrl", imageUrl)

        // Este JSON es el que enviaremos a Firebase
        val jsonString = profileData.toString()

        println(jsonString)
    }

    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

}