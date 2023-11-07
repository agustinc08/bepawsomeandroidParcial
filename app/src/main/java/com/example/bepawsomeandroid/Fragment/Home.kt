package com.example.bepawsomeandroid.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
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
import org.json.JSONException
import org.json.JSONObject

class Home : Fragment() {

    private val animalViewModel: AnimalViewModel by lazy {
        ViewModelProvider(this, AnimalViewModelFactory()).get(AnimalViewModel::class.java)
    }

    private lateinit var animalButtonsLayout: LinearLayout
    var jsonObject: JSONObject? = null
    var userObject: User? = null
    var gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = requireContext().getSharedPreferences("Credenciales", Context.MODE_PRIVATE)
        val jsonObjectString = sharedPref.getString("UserLogueado", null)

        if (jsonObjectString != null) {
            try {
                jsonObject = JSONObject(jsonObjectString)
                userObject = gson.fromJson(jsonObject.toString(), User::class.java)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        animalButtonsLayout = view.findViewById(R.id.animalButtonsLayout)

        animalViewModel.leerAnimalesDesdeFirebase(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (animalSnapshot in snapshot.children) {
                    val animal = animalSnapshot.getValue(Animal::class.java)
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
        val likeButton = customView.findViewById<Button>(R.id.likeButton)
        val imageView = customView.findViewById<ImageView>(R.id.animalImageView)

        nameTextView.text = "Nombre: ${animal.nombre}"
        breedTextView.text = "Raza: ${animal.raza}"
        ageTextView.text = "Edad: ${animal.edad}"
        sexTextView.text = "Sexo: ${animal.sexo}"

        Glide.with(this)
            .load(animal.imgUrl1)
            .into(imageView)

        var isLiked = false

        likeButton.setOnClickListener {
            if (isLiked) {
                likeButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_700))
                //aca tengo que sacarle el objeto de la lista de fav
                userObject?.eliminarAnimalDeFavoritos(animal)
                isLiked = false
            } else {
                likeButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue_menu))
                //aca es donde lo guardo en la lista de fav
                userObject?.agregarAnimalAFavoritos(animal)

                //renderizar en la otra vista

                isLiked = true
            }
        }

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
