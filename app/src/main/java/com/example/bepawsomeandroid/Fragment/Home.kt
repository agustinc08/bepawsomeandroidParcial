package com.example.bepawsomeandroid.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.bepawsomeandroid.Models.Animal
import com.example.bepawsomeandroid.R
import com.example.bepawsomeandroid.ViewModels.AnimalViewModel
import com.example.bepawsomeandroid.ViewModels.AnimalViewModelFactory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Home : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private val animalViewModel: AnimalViewModel by lazy {
        ViewModelProvider(this, AnimalViewModelFactory()).get(AnimalViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Llama a la función para leer animales desde Firebase
        animalViewModel.leerAnimalesDesdeFirebase(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Procesa los datos de Firebase y muestra los animales en la consola o en tu interfaz de usuario
                for (animalSnapshot in snapshot.children) {
                    val animal = animalSnapshot.getValue(Animal::class.java)
                    if (animal != null) {
                        // Muestra los datos del animal por consola
                        println("Nombre del animal: ${animal.nombre}")
                        println("Ubicación del animal: ${animal.ubicacion}")
                        println("Ubicación del animal: ${animal}")
                        // Continúa mostrando otros datos del animal según tu modelo
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error al leer datos desde Firebase: ${error.message}")
            }
            })
        }
}