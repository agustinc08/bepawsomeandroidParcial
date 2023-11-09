package com.example.bepawsomeandroid.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bepawsomeandroid.Api.DogApiService
import com.example.bepawsomeandroid.Api.DogBreedsResponse
import com.example.bepawsomeandroid.Models.Animal
import com.example.bepawsomeandroid.R
import com.example.bepawsomeandroid.ViewModels.AnimalViewModel
import com.example.bepawsomeandroid.ViewModels.AnimalViewModelFactory
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Publication : Fragment() {
    private lateinit var editTextNombre: EditText
    private lateinit var editTextUbicacion: EditText
    private lateinit var editTextRaza: EditText
    private lateinit var radioGroupSexo: RadioGroup
    private lateinit var radioButtonMacho: RadioButton
    private lateinit var radioButtonHembra: RadioButton
    private lateinit var editTextPeso: EditText
    private lateinit var editTextEdad: EditText
    private lateinit var buttonGuardar: Button
    private lateinit var buttonAddImage: Button
    private lateinit var imageInputLayout: LinearLayout
    private lateinit var spinnerRazas: Spinner

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: DogApiService by lazy {
        retrofit.create(DogApiService::class.java)
    }

    private val animalViewModel: AnimalViewModel by lazy {
        ViewModelProvider(this, AnimalViewModelFactory()).get(AnimalViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_publication, container, false)
    }

    // En el método onViewCreated, después de inicializar otras vistas, inicializa el Spinner y carga los datos
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Buscar las vistas por ID
        spinnerRazas = view.findViewById(R.id.spinnerRazas)
        editTextNombre = view.findViewById(R.id.editTextNombre)
        editTextUbicacion = view.findViewById(R.id.editTextUbicacion)
        editTextRaza = view.findViewById(R.id.razaPerro)
        radioGroupSexo = view.findViewById(R.id.radioGroupSexo)
        radioButtonMacho = view.findViewById(R.id.radioButtonMacho)
        radioButtonHembra = view.findViewById(R.id.radioButtonHembra)
        editTextPeso = view.findViewById(R.id.editTextPeso)
        editTextEdad = view.findViewById(R.id.editTextEdad)
        buttonGuardar = view.findViewById(R.id.buttonGuardar)
        imageInputLayout = view.findViewById(R.id.imageInputLayout)

        val publicationList = JSONArray()

        spinnerRazas = view.findViewById(R.id.spinnerRazas)

        apiService.getBreeds().enqueue(object : Callback<DogBreedsResponse> {
            override fun onResponse(call: Call<DogBreedsResponse>, response: Response<DogBreedsResponse>) {
                if (response.isSuccessful) {
                    val breedsResponse = response.body()
                    breedsResponse?.message?.let { breedsMap ->
                        val breedList = breedsMap.keys.toList()
                        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, breedList)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerRazas.adapter = adapter
                    }
                } else {
                    // Manejar errores de la respuesta de la API
                }
            }

            override fun onFailure(call: Call<DogBreedsResponse>, t: Throwable) {
                // Manejar errores de la llamada a la API
            }
        })


        buttonGuardar.setOnClickListener {
            // Obtener los valores de los campos de entrada
            val nombre = editTextNombre.text.toString()
            val ubicacion = editTextUbicacion.text.toString()
            val raza = editTextRaza.text.toString()
            val sexo = if (radioButtonMacho.isChecked) "Macho" else "Hembra"
            val peso = editTextPeso.text.toString().toDoubleOrNull() ?: 0.0
            val edad = editTextEdad.text.toString().toIntOrNull() ?: 0


            // Crear una instancia de Animal con los datos ingresados por el usuario
            val nuevoAnimal = Animal("nombre", "ubicacion", "sexo", 0.0, 0, "raza", "subraza", "usuarioId")

            // Guardar el animal en Firebase
            val animalesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("animales")
            val nuevoAnimalId = animalesRef.push().key
            nuevoAnimalId?.let {
                animalesRef.child(it).setValue(nuevoAnimal)
                    .addOnSuccessListener {
                        // Animal guardado exitosamente en Firebase
                        Toast.makeText(requireContext(), "Publicación guardada exitosamente.", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        // Error al guardar el animal en Firebase
                        Toast.makeText(requireContext(), "Error al guardar la publicación.", Toast.LENGTH_SHORT).show()
                    }
            }

            // Limpia los campos después de guardar
            clearFields()
        }
    }
    private fun clearFields() {
        editTextNombre.text.clear()
        editTextUbicacion.text.clear()
        radioGroupSexo.clearCheck()
        editTextPeso.text.clear()
        editTextEdad.text.clear()
    }
}