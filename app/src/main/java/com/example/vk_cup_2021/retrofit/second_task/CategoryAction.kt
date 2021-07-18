package com.example.example

import com.google.gson.annotations.SerializedName

   
data class CategoryAction (

   @SerializedName("action") var action : Action,
   @SerializedName("name") var name : String

)