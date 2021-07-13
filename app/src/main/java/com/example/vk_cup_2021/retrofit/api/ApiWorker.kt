package com.example.vk_cup_2021.retrofit.api

import android.content.Context
import com.example.example.Recommendation
import com.example.vk_cup_2021.modules.Notifier
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiWorker{

    private val TOKEN = "f3a52e73940553ef67d31a4274d475e0d192e65cf48be9e7f0493bae5fcdf3871a75c1fa8fd85ede6cadf"

    private var retrofit: Retrofit
    private var api: RecommendApi
    private var context: Context

    private lateinit var recommendation: Recommendation

    constructor(c: Context){
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.vk.com/method/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(RecommendApi::class.java)
        context = c
    }

    fun getRecommend(){
        var call = api.getRecommendations("5.52", TOKEN, 20)
        call.enqueue(object: Callback<Recommendation>{
            override fun onFailure(call: Call<Recommendation>, t: Throwable) {
                Notifier.showToast(context, "Cannot load newsfeed")
            }

            override fun onResponse(
                call: Call<Recommendation>,
                response: Response<Recommendation>
            ) {
                if (response.isSuccessful){
                    recommendation = response.body()!!
                }
            }
        })
    }

}