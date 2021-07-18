package com.example.example

import com.google.gson.annotations.SerializedName

   
data class Response (

   @SerializedName("items") var items : List<Items>,
   @SerializedName("profiles") var profiles : List<Profiles>,
   @SerializedName("groups") var groups : List<Groups>,
   @SerializedName("next_from") var nextFrom : String

)