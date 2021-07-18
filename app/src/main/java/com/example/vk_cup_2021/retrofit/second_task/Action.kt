package com.example.example

import com.google.gson.annotations.SerializedName

   
data class Action (

   @SerializedName("target") var target : String,
   @SerializedName("type") var type : String,
   @SerializedName("url") var url : String

)