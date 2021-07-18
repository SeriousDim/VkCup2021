package com.example.example

import com.google.gson.annotations.SerializedName

   
data class Attachments (

   @SerializedName("type") var type : String,
   @SerializedName("photo") var photo : Photo

)