package com.example.bepawsomeandroid.ViewModels

import com.example.bepawsomeandroid.Api.DogApiService
import com.example.bepawsomeandroid.Models.Animal
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call


class AnimalViewModel {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: DogApiService = retrofit.create(DogApiService::class.java)
    private val database = FirebaseDatabase.getInstance().reference.child("animales")

    fun obtenerAnimalesDeApi(): Call<Animal> {
        return apiService.getAnimals()
    }

    fun guardarAnimalesEnFirebase(animal: Animal) {
        database.setValue(animal)
    }
}