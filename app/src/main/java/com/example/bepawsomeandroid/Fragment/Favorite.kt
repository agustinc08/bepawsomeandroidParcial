package com.example.bepawsomeandroid.Fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.LinearLayout
import com.example.bepawsomeandroid.R
import com.example.bepawsomeandroid.Models.User
import com.example.bepawsomeandroid.Models.Animal
import com.google.gson.Gson
import com.bumptech.glide.Glide
import org.json.JSONObject
import androidx.fragment.app.Fragment
import org.json.JSONException

class Favorite : Fragment() {

    private var userObject: User? = null
    private lateinit var animalButtonsLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animalButtonsLayout = view.findViewById(R.id.animalButtonsLayout) // Ajusta el ID según tu XML

        // Cargar el usuario desde las preferencias
        val sharedPref = requireContext().getSharedPreferences("Credenciales", Context.MODE_PRIVATE)
        val jsonObjectString = sharedPref.getString("UserLogueado", null)

        if (jsonObjectString != null) {
            try {
                val gson = Gson()
                val jsonObject = JSONObject(jsonObjectString)
                userObject = gson.fromJson(jsonObject.toString(), User::class.java)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        userObject?.favoritos?.forEach { animal ->
            val customView = createCustomAnimalView(animal)
            animalButtonsLayout.addView(customView)
        }
    }

    private fun createCustomAnimalView(animal: Animal): View {
        val customView = layoutInflater.inflate(R.layout.custom_animal_view, null)
        val nameTextView = customView.findViewById<TextView>(R.id.animalNameTextView)
        val breedTextView = customView.findViewById<TextView>(R.id.animalBreedTextView)
        val ageTextView = customView.findViewById<TextView>(R.id.animalAgeTextView)
        val sexTextView = customView.findViewById<TextView>(R.id.animalSexTextView)

        nameTextView.text = "Nombre: ${animal.nombre}"
        breedTextView.text = "Raza: ${animal.raza}"
        ageTextView.text = "Edad: ${animal.edad}"
        sexTextView.text = "Sexo: ${animal.sexo}"

        val imageView = customView.findViewById<ImageView>(R.id.animalImageView)
        Glide.with(this).load(animal.imgUrl1).into(imageView)

        return customView
    }
}
