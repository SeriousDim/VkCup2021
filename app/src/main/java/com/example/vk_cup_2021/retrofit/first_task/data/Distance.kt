package com.example.example

import com.google.gson.annotations.SerializedName

   
data class Distance (

   @SerializedName("text") var text : String,
   @SerializedName("value") var value : Int

)