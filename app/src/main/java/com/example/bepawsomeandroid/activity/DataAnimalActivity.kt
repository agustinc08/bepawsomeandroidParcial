package com.example.bepawsomeandroid.Activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bepawsomeandroid.Models.Animal
import com.google.firebase.database.*
import com.bumptech.glide.Glide
import android.Manifest
import com.example.bepawsomeandroid.R

class DataAnimalActivity : AppCompatActivity() {
    private lateinit var animalImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var sexTextView: TextView  // Agrega esta línea para inicializar sexTextView
    private lateinit var razaTextView: TextView
    private lateinit var subRazaTextView: TextView
    private val telefonoAnimal = "123456789"


    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_animal)

        // Inicializar las vistas
        animalImageView = findViewById(R.id.animalImageView)
        nameTextView = findViewById(R.id.nameTextView)
        ageTextView = findViewById(R.id.ageTextView)
        sexTextView = findViewById(R.id.sexTextView)  // Inicializa sexTextView con la vista correspondiente del layout
        razaTextView = findViewById(R.id.razaTextView)
        subRazaTextView = findViewById(R.id.subRazaTextView)

        // Recuperar el ID del animal de la intent
        val animalId = intent.getStringExtra("animalId")

        // Imprimir el ID del animal en la consola
        println("Animal ID: $animalId")

        // Verificar que animalId no sea nulo antes de usarlo en Firebase Database
        if (animalId != null) {
            // Inicializar la referencia a la base de datos
            databaseReference = FirebaseDatabase.getInstance().reference.child("animales").child(animalId)

            // Obtener y mostrar los datos del animal desde Firebase
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val animal = snapshot.getValue(Animal::class.java)
                    if (animal != null) {
                        Glide.with(this@DataAnimalActivity)
                            .load(animal)
                            .into(animalImageView)

                        // Mostrar la imagen grande antes que el nombre y la edad
                        nameTextView.text = "Nombre: ${animal.nombre}"
                        ageTextView.text = "Edad: ${animal.edad}"
                        razaTextView.text = "Raza: ${animal.raza}"
                        subRazaTextView.text = "SubRaza: ${animal.subraza}"  // Asegúrate de tener un atributo "subraza" en tu clase Animal
                        sexTextView.text = "Sexo: ${animal.sexo}"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Error al leer datos desde Firebase: ${error.message}")
                }
            })
        } else {
            // Manejar el caso en el que animalId es nulo, por ejemplo, mostrar un mensaje de error o volver atrás
        }

        val callButton = findViewById<Button>(R.id.callButton)
        callButton.setOnClickListener {
            // Verifica si tienes permiso para realizar llamadas
            if (ContextCompat.checkSelfPermission(
                    this@DataAnimalActivity,
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Si tienes permiso, inicia la llamada
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$telefonoAnimal"))
                startActivity(intent)
            } else {
                // Si no tienes permiso, solicita el permiso al usuario
                ActivityCompat.requestPermissions(
                    this@DataAnimalActivity,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    CALL_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    companion object {
        private const val CALL_PERMISSION_REQUEST_CODE = 101
    }

    }

