package com.example.vk_cup_2021.retrofit.api

import com.example.example.Recommendation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecommendApi {

    @GET("newsfeed.getRecommended")
    // v=5.52&access_token=f3a52e73940553ef67d31a4274d475e0d192e65cf48be9e7f0493bae5fcdf3871a75c1fa8fd85ede6cadf&count=20
    fun getRecommendations(@Query("v") v: String = "5.52",
                           @Query("access_token") accessToken: String,
                           @Query("count") count: Int): Call<Recommendation>

}