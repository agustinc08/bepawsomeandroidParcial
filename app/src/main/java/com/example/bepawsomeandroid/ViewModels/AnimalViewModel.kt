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

    fun leerAnimalesDesdeFirebase(listener: ValueEventListener) {
        database.addListenerForSingleValueEvent(listener)
    }
}