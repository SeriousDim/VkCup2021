package com.example.example

import com.google.gson.annotations.SerializedName

   
data class Groups (

   @SerializedName("id") var id : Long,
   @SerializedName("name") var name : String,
   @SerializedName("screen_name") var screenName : String,
   @SerializedName("is_closed") var isClosed : Int,
   @SerializedName("type") var type : String,
   @SerializedName("photo_50") var photo50 : String,
   @SerializedName("photo_100") var photo100 : String,
   @SerializedName("photo_200") var photo200 : String

)