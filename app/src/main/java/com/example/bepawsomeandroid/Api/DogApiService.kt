package com.example.bepawsomeandroid.Api

import com.example.bepawsomeandroid.Models.Animal
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path

interface DogApiService {
    @GET("breeds/list/all")
    fun getBreeds(): Call<DogBreedsResponse>

    @GET("breed/{breed}/images/random/{count}")
    fun getDogImagesByBreed(@Path("breed") breed: String, @Path("count") count: Int): Call<DogApiResponse>

    @GET("breed/{breed}/list")
    fun getSubBreeds(@Path("breed") breed: String): Call<SubBreedsResponse>

}