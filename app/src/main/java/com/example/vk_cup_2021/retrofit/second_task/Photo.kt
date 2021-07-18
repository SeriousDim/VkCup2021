package com.example.example

import com.google.gson.annotations.SerializedName

   
data class Photo (

   @SerializedName("album_id") var albumId : Long,
   @SerializedName("date") var date : Long,
   @SerializedName("id") var id : Long,
   @SerializedName("owner_id") var ownerId : Long,
   @SerializedName("has_tags") var hasTags : Boolean,
   @SerializedName("access_key") var accessKey : String,
   @SerializedName("height") var height : Int,
   @SerializedName("photo_1280") var photo1280 : String,
   @SerializedName("photo_130") var photo130 : String,
   @SerializedName("photo_2560") var photo2560 : String,
   @SerializedName("photo_604") var photo604 : String,
   @SerializedName("photo_75") var photo75 : String,
   @SerializedName("photo_807") var photo807 : String,
   @SerializedName("post_id") var postId : Long,
   @SerializedName("text") var text : String,
   @SerializedName("user_id") var userId : Long,
   @SerializedName("width") var width : Long

)