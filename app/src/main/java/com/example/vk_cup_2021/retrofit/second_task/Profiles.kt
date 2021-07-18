package com.example.example

import com.google.gson.annotations.SerializedName

   
data class Profiles (

   @SerializedName("first_name") var firstName : String,
   @SerializedName("id") var id : Long,
   @SerializedName("last_name") var lastName : String,
   @SerializedName("sex") var sex : Int,
   @SerializedName("screen_name") var screenName : String,
   @SerializedName("photo_50") var photo50 : String,
   @SerializedName("photo_100") var photo100 : String,
   @SerializedName("online_info") var onlineInfo : OnlineInfo,
   @SerializedName("online") var online : Long

)