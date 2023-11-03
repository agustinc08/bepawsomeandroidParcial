package com.example.bepawsomeandroid.api

import com.example.bepawsomeandroid.model.DogModel
import retrofit2.http.GET

interface ApiService {
    @GET("https://dog.ceo/api/breeds/list/all")
    suspend fun listarRazas(): List<DogModel>

}