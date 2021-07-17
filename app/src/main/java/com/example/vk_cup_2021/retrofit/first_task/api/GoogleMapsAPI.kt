package com.example.vk_cup_2021.retrofit.first_task.api

import com.example.example.GoogleDirections
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleMapsAPI {

    @GET("api/directions/json")
    fun getDirections(@Query("origin") origin: String,
                @Query("destination") destination: String,
                @Query("key") key: String,
                @Query("mode") mode: String = "driving",
                @Query("language") lang: String = "RU"): Call<GoogleDirections>

}