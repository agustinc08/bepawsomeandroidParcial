package com.example.bepawsomeandroid.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.bepawsomeandroid.Activity.DataAnimalActivity
import com.example.bepawsomeandroid.Models.Animal
import com.example.bepawsomeandroid.Models.User
import com.example.bepawsomeandroid.R
import com.example.bepawsomeandroid.ViewModels.AnimalViewModel
import com.example.bepawsomeandroid.ViewModels.AnimalViewModelFactory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class Home : Fragment() {

    lateinit var nameUserCredential: String
    lateinit var imageUrlUserCredential: String


    private val animalViewModel: AnimalViewModel by lazy {
        ViewModelProvider(this, AnimalViewModelFactory()).get(AnimalViewModel::class.java)
    }

    private lateinit var animalButtonsLayout: LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val sharedPreferences = requireContext().getSharedPreferences("Credenciales", Context.MODE_PRIVATE)

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
        imageUrlUserCredential = userObject.imageUrl

        val textViewUserName = view.findViewById<TextView>(R.id.textViewNombreUsuario)
        textViewUserName.text = nameUserCredential

        val imageViewUser = view.findViewById<ImageView>(R.id.imageViewUserProfile2)
        if (imageUrlUserCredential.isNotEmpty()) {
            Picasso.get().load(imageUrlUserCredential).into(imageViewUser)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        animalButtonsLayout = view.findViewById(R.id.animalButtonsLayout)

        animalViewModel.leerAnimalesDesdeFirebase(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (animalSnapshot in snapshot.children) {
                    val animal = animalSnapshot.getValue(Animal::class.java)
                    println("Error al leer datos desde Firebase: ${animal}")
                    if (animal != null) {
                        val customView = createCustomAnimalView(animalSnapshot.key!!, animal)
                        animalButtonsLayout.addView(customView)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error al leer datos desde Firebase: ${error.message}")
            }
        })
    }



    private fun createCustomAnimalView(animalId: String, animal: Animal): View {
        val customView = layoutInflater.inflate(R.layout.custom_animal_view, null)
        val nameTextView: TextView = customView.findViewById(R.id.animalNameTextView)
        val breedTextView: TextView = customView.findViewById(R.id.animalBreedTextView)
        val ageTextView: TextView = customView.findViewById(R.id.animalAgeTextView)
        val sexTextView: TextView = customView.findViewById(R.id.animalSexTextView)
        val imageView = customView.findViewById<ImageView>(R.id.animalImageView)

        nameTextView.text = "Nombre: ${animal.nombre}"
        breedTextView.text = "Raza: ${animal.raza}"
        ageTextView.text = "Edad: ${animal.edad}"
        sexTextView.text = "Sexo: ${animal.sexo}"

        Glide.with(this)
            .load(animal.imgUrl1)
            .into(imageView)

        customView.setOnClickListener {
            val intent = Intent(requireContext(), DataAnimalActivity::class.java)

            // Pasar el ID del perro a DataAnimalActivity
            intent.putExtra("animalId", animalId)
            println("Animal ID: $animalId") // Imprimir el ID antes de iniciar la actividad


            startActivity(intent)
        }
        return customView
        }
}