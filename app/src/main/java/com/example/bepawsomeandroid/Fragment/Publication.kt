package com.example.bepawsomeandroid.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.bepawsomeandroid.Models.Animal
import com.example.bepawsomeandroid.ViewModels.AnimalViewModel
import com.example.bepawsomeandroid.ViewModels.AnimalViewModelFactory
import com.example.bepawsomeandroid.R

class Publication : Fragment() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextUbicacion: EditText
    private lateinit var editTextRaza: EditText
    private lateinit var editTextSubraza: EditText
    private lateinit var radioGroupSexo: RadioGroup
    private lateinit var radioButtonMacho: RadioButton
    private lateinit var radioButtonHembra: RadioButton
    private lateinit var editTextPeso: EditText
    private lateinit var editTextEdad: EditText
    private lateinit var buttonGuardar: Button
    private lateinit var buttonAddImage: Button
    private lateinit var imageInputLayout: LinearLayout
    private var imageInputCount = 1
    private var imgUrl1: String = ""

    // Inicializar AnimalViewModel
    private val animalViewModel: AnimalViewModel by lazy {
        ViewModelProvider(this, AnimalViewModelFactory()).get(AnimalViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publication, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextImagen1 = view.findViewById<EditText>(R.id.editTextImagen1)

        // Agregar un listener para el primer EditText
        editTextImagen1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Actualizar imgUrl1 cuando el usuario ingrese una URL en el primer campo de imagen
                imgUrl1 = s.toString()
            }
        })

        // Buscar las vistas por ID
        editTextNombre = view.findViewById(R.id.editTextNombre)
        editTextUbicacion = view.findViewById(R.id.editTextUbicacion)
        editTextRaza = view.findViewById(R.id.razaPerro)
        editTextSubraza = view.findViewById(R.id.subrazaPerro)
        radioGroupSexo = view.findViewById(R.id.radioGroupSexo)
        radioButtonMacho = view.findViewById(R.id.radioButtonMacho)
        radioButtonHembra = view.findViewById(R.id.radioButtonHembra)
        editTextPeso = view.findViewById(R.id.editTextPeso)
        editTextEdad = view.findViewById(R.id.editTextEdad)
        buttonGuardar = view.findViewById(R.id.buttonGuardar)
        buttonAddImage = view.findViewById(R.id.buttonAddImage)
        imageInputLayout = view.findViewById(R.id.imageInputLayout)

        // Agregar acciones para el botón "Guardar Publicación"
        buttonGuardar.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val ubicacion = editTextUbicacion.text.toString()
            val raza = editTextRaza.text.toString()
            val subraza = editTextSubraza.text.toString()
            val selectedSexId = radioGroupSexo.checkedRadioButtonId
            val peso = editTextPeso.text.toString()
            val edad = editTextEdad.text.toString()
            val editTextImagen1 = view.findViewById<EditText>(R.id.editTextImagen1)

            if (nombre.isEmpty() || ubicacion.isEmpty() || raza.isEmpty() || selectedSexId == -1 || peso.isEmpty() || edad.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, complete todos los campos obligatorios.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedSex = if (selectedSexId == R.id.radioButtonMacho) "Macho" else "Hembra"

            if (edad.toInt() <= 0) {
                Toast.makeText(requireContext(), "La edad debe ser mayor a 0.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val imgUrls = mutableListOf<String>()
            for (i in 1..imageInputCount) {
                val editText = imageInputLayout.findViewWithTag<EditText>("editTextImagen$i")
                val imgUrl = editText?.text.toString() // Verificar si la vista es nula
                if (imgUrl.isNotEmpty()) {
                    imgUrls.add(imgUrl)
                }
            }

            // Crear un objeto Animal con los datos de la publicación
            val animal = Animal(
                nombre = nombre,
                ubicacion = ubicacion,
                sexo = selectedSex,
                peso = peso.toDouble(),
                edad = edad.toInt(),
                raza = raza,
                subRaza = subraza,
                imgUrl1 = imgUrls.getOrElse(0) { "N/A" }, // Usar "N/A" si la URL está vacía
                imgUrl2 = imgUrls.getOrElse(1) { "N/A" },
                imgUrl3 = imgUrls.getOrElse(2) { "N/A" },
                imgUrl4 = imgUrls.getOrElse(3) { "N/A" }
            )

            // Guardar el animal en Firebase
            animalViewModel.guardarAnimalEnFirebase(animal)

            // Limpiar el formulario
            editTextNombre.text.clear()
            editTextUbicacion.text.clear()
            editTextRaza.text.clear()
            radioGroupSexo.clearCheck()
            editTextPeso.text.clear()
            editTextEdad.text.clear()

            Toast.makeText(requireContext(), "Publicación guardada correctamente.", Toast.LENGTH_SHORT).show()
        }

        // Agregar acción para el botón "Agregar Imagen"
        buttonAddImage.setOnClickListener {
            if (imageInputCount < 5) { // Limitar a 5 campos de imagen
                imageInputCount++
                addImageInput(imageInputCount)
            } else {
                Toast.makeText(requireContext(), "No se pueden agregar más de 5 imágenes.", Toast.LENGTH_SHORT).show()
                buttonAddImage.isEnabled = false // Deshabilitar el botón después de 5 campos
            }
        }
    }

    private fun addImageInput(imageNumber: Int) {
        val newEditText = EditText(requireContext())
        newEditText.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        newEditText.hint = "URL de la Imagen $imageNumber"
        newEditText.inputType = InputType.TYPE_TEXT_VARIATION_URI
        newEditText.setSingleLine()
        newEditText.tag = "editTextImagen$imageNumber" // Establecer una etiqueta para identificar el EditText
        imageInputLayout.addView(newEditText)
    }
}
