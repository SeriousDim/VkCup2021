package com.example.example

import com.google.gson.annotations.SerializedName

   
data class Reposts (

   @SerializedName("count") var count : Int,
   @SerializedName("user_reposted") var userReposted : Int

)