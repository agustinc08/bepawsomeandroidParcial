package com.example.bepawsomeandroid.ViewModels

import androidx.lifecycle.ViewModel
import com.example.bepawsomeandroid.Api.DogApiService
import com.example.bepawsomeandroid.Models.Animal
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call


class AnimalViewModel : ViewModel() {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: DogApiService = retrofit.create(DogApiService::class.java)
    private val database = FirebaseDatabase.getInstance().reference.child("animales")

    fun obtenerAnimalesDeApi(): Call<Animal> {
        return apiService.getAnimals()
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
}