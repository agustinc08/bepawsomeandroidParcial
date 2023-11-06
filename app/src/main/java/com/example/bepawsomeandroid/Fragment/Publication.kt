package com.example.bepawsomeandroid.Fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bepawsomeandroid.R
import com.example.bepawsomeandroid.ViewModels.AnimalViewModel
import com.example.bepawsomeandroid.ViewModels.AnimalViewModelFactory
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
                imgUrl1 = s.toString()
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
        buttonAddImage = view.findViewById(R.id.buttonAddImage)
        imageInputLayout = view.findViewById(R.id.imageInputLayout)

        val publicationList = JSONArray()

        // Agregar acciones para el botón "Guardar Publicación"
        buttonGuardar.setOnClickListener {
            // Coloca aquí el código para guardar la publicación en el JSONArray y ViewModel
            // ...

            // Limpia los campos después de guardar
            clearFields()
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
        newEditText.id = View.generateViewId()
        imageInputLayout.addView(newEditText)
    }

    private fun clearFields() {
        editTextNombre.text.clear()
        editTextUbicacion.text.clear()
        radioGroupSexo.clearCheck()
        editTextPeso.text.clear()
        editTextEdad.text.clear()
    }
}
