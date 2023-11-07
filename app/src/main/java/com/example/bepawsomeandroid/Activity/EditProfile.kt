package com.example.bepawsomeandroid.Activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.bepawsomeandroid.R
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