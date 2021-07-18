package com.example.example

import com.google.gson.annotations.SerializedName

   
data class Likes (

   @SerializedName("count") var count : Int,
   @SerializedName("user_likes") var userLikes : Int,
   @SerializedName("can_like") var canLike : Int,
   @SerializedName("can_publish") var canPublish : Int

)