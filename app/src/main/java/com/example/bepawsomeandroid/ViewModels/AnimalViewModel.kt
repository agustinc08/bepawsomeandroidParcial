package com.example.bepawsomeandroid.ViewModels

import androidx.lifecycle.ViewModel
import com.example.bepawsomeandroid.Api.DogApiService
import com.example.bepawsomeandroid.Api.DogBreedsResponse
import com.example.bepawsomeandroid.Api.SubBreedsResponse
import com.example.bepawsomeandroid.Models.Animal
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AnimalViewModel : ViewModel() {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: DogApiService = retrofit.create(DogApiService::class.java)
    private val database = FirebaseDatabase.getInstance().reference.child("animales")

    fun obtenerAnimalesDeApi(): Call<DogBreedsResponse> {
        return apiService.getBreeds()
    }

    fun obtenerSubrazasDeApi(raza: String): Call<SubBreedsResponse> {
        return apiService.getSubBreeds(raza)
    }

    fun obtenerAnimalPorId(animalId: String, listener: ValueEventListener) {
        val animalReference = database.child("animales").child(animalId)
        animalReference.addListenerForSingleValueEvent(listener)
    }

    fun leerAnimalesDesdeFirebase(listener: ValueEventListener) {
        database.addListenerForSingleValueEvent(listener)
    }
    fun guardarAnimalEnFirebase(animal: Animal) {
        val animalKey = database.child("animales").push().key
        if (animalKey != null) {
            database.child("animales").child(animalKey).setValue(animal)
        }
    }
    fun tieneSubrazas(raza: String, callback: (Boolean) -> Unit) {
        obtenerSubrazasDeApi(raza).enqueue(object : Callback<SubBreedsResponse> {
            override fun onResponse(call: Call<SubBreedsResponse>, response: Response<SubBreedsResponse>) {
                if (response.isSuccessful) {
                    val subBreedsResponse = response.body()
                    val tieneSubrazas = !subBreedsResponse?.message.isNullOrEmpty()
                    callback(tieneSubrazas)
                } else {
                    // Manejar errores de la respuesta de la API para subrazas
                    callback(false)
                }
            }

            override fun onFailure(call: Call<SubBreedsResponse>, t: Throwable) {
                // Manejar errores de la llamada a la API para subrazas
                callback(false)
            }
        })
    }
}