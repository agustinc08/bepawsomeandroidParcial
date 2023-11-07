package com.example.bepawsomeandroid.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.example.bepawsomeandroid.Models.Animal
import com.google.firebase.database.*
import com.bumptech.glide.Glide
import com.example.bepawsomeandroid.R

class DataAnimalActivity : AppCompatActivity() {
    private lateinit var animalImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var sexTextView: TextView

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_animal)

        // Inicializar las vistas
        animalImageView = findViewById(R.id.animalImageView)
        nameTextView = findViewById(R.id.nameTextView)
        ageTextView = findViewById(R.id.ageTextView)
        sexTextView = findViewById(R.id.sexTextView)

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
                            .load(animal.imgUrl1)
                            .into(animalImageView)

                        // Mostrar la imagen grande antes que el nombre y la edad
                        nameTextView.text = "Nombre: ${animal.nombre}"
                        ageTextView.text = "Edad: ${animal.edad}"
                        sexTextView.text = "Sexo: ${animal.sexo}"
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
    }
}
