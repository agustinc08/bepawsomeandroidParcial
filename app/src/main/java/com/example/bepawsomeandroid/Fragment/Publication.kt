package com.example.bepawsomeandroid.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bepawsomeandroid.Models.Animal
import com.example.bepawsomeandroid.R
import com.example.bepawsomeandroid.ViewModels.AnimalViewModel
import com.example.bepawsomeandroid.ViewModels.AnimalViewModelFactory
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONArray

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
    private var imageInputCount = 1

    private val animalViewModel: AnimalViewModel by lazy {
        ViewModelProvider(this, AnimalViewModelFactory()).get(AnimalViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_publication, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextImagen1 = view.findViewById<EditText>(R.id.editTextImagen1)

        editTextImagen1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Actualizar imgUrl1 cuando el usuario ingrese una URL en el primer campo de imagen
                var imgUrl1 = s.toString()
            }
        })

        // Buscar las vistas por ID
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

        buttonGuardar.setOnClickListener {
            // Obtener los valores de los campos de entrada
            val nombre = editTextNombre.text.toString()
            val ubicacion = editTextUbicacion.text.toString()
            val raza = editTextRaza.text.toString()
            val sexo = if (radioButtonMacho.isChecked) "Macho" else "Hembra"
            val peso = editTextPeso.text.toString().toDoubleOrNull() ?: 0.0
            val edad = editTextEdad.text.toString().toIntOrNull() ?: 0

            // Obtener las URL de las imágenes desde los campos de texto
            val imgUrl1 = view.findViewById<EditText>(R.id.editTextImagen1).text.toString()
            val imgUrl2 = view.findViewById<EditText>(R.id.editTextImagen2).text.toString()
            val imgUrl3 = view.findViewById<EditText>(R.id.editTextImagen3).text.toString()
            val imgUrl4 = view.findViewById<EditText>(R.id.editTextImagen4).text.toString()

            // Crear una instancia de Animal con los datos ingresados por el usuario
            val nuevoAnimal = Animal(nombre, ubicacion, sexo, peso, edad, raza, imgUrl1, imgUrl2, imgUrl3, imgUrl4)

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