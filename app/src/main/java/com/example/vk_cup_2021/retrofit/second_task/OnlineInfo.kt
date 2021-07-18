package com.example.example

import com.google.gson.annotations.SerializedName

   
data class OnlineInfo (

   @SerializedName("visible") var visible : Boolean,
   @SerializedName("is_online") var isOnline : Boolean,
   @SerializedName("is_mobile") var isMobile : Boolean

)