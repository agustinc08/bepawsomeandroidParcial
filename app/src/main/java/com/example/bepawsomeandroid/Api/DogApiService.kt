package com.example.bepawsomeandroid.Api

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path

interface DogApiService {
    @GET("breeds/list/all")
    fun getBreeds(): Call<DogBreedsResponse>
    @GET("breed/{breed}/images/random/3")
    fun getDogImages(@Path("breed") breed: String?): Call<DogApiResponse>
    @GET("breed/{breed}/list")
    fun getSubBreeds(@Path("breed") breed: String): Call<SubBreedsResponse>

}