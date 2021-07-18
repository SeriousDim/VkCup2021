package com.example.example

import com.google.gson.annotations.SerializedName

   
data class Comments (

   @SerializedName("count") var count : Int,
   @SerializedName("can_post") var canPost : Int

)