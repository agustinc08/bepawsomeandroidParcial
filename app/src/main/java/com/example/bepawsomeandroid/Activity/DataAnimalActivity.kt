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

    private lateinit var nombreTextView: TextView
    private lateinit var ubicacionTextView: TextView
    private lateinit var sexoTextView: TextView
    private lateinit var pesoTextView: TextView
    private lateinit var edadTextView: TextView
    private lateinit var razaTextView: TextView
    private lateinit var imageView1: ImageView
    private lateinit var imageView2: ImageView
    private lateinit var imageView3: ImageView
    private lateinit var imageView4: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_animal)

        // Obtener referencia a las vistas
        nombreTextView = findViewById(R.id.nombreTextView)
        ubicacionTextView = findViewById(R.id.ubicacionTextView)
        sexoTextView = findViewById(R.id.sexoTextView)
        pesoTextView = findViewById(R.id.pesoTextView)
        edadTextView = findViewById(R.id.edadTextView)
        razaTextView = findViewById(R.id.razaTextView)
        imageView1 = findViewById(R.id.imageView1)
        imageView2 = findViewById(R.id.imageView2)
        imageView3 = findViewById(R.id.imageView3)
        imageView4 = findViewById(R.id.imageView4)

        // Obtener el ID del animal desde Intent (reemplaza 'animalId' con el nombre correcto de la clave)
        val animalId = intent.getStringExtra("animalId")

        // Referencia a la base de datos de Firebase
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("animales").child(animalId!!)

        // Escuchar cambios en los datos del animal
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Obtener objeto Animal desde Firebase
                    val animal: Animal = dataSnapshot.getValue(Animal::class.java)!!

                    // Mostrar datos del animal en las vistas
                    nombreTextView.text = "Nombre: ${animal.nombre}"
                    ubicacionTextView.text = "Ubicación: ${animal.ubicacion}"
                    sexoTextView.text = "Sexo: ${animal.sexo}"
                    pesoTextView.text = "Peso: ${animal.peso} kg"
                    edadTextView.text = "Edad: ${animal.edad} años"
                    razaTextView.text = "Raza: ${animal.raza}"

                    // Cargar imágenes usando Glide (asegúrate de tener las URLs de las imágenes en el objeto Animal)
                    Glide.with(this@DataAnimalActivity).load(animal.imgUrl1).into(imageView1)
                    Glide.with(this@DataAnimalActivity).load(animal.imgUrl2).into(imageView2)
                    Glide.with(this@DataAnimalActivity).load(animal.imgUrl3).into(imageView3)
                    Glide.with(this@DataAnimalActivity).load(animal.imgUrl4).into(imageView4)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar errores de Firebase, si es necesario
            }
        })
    }
}
