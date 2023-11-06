package com.example.bepawsomeandroid.Api

import com.example.bepawsomeandroid.Models.Animal
import retrofit2.http.GET
import retrofit2.Call

interface DogApiService {
    @GET("breeds/list/all")
    fun getAnimals(): Call<Animal>
}