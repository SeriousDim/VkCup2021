package com.example.example

import com.google.gson.annotations.SerializedName

   
data class Items (

   @SerializedName("source_id") var sourceId : Long,
   @SerializedName("date") var date : Long,
   @SerializedName("can_doubt_category") var canDoubtCategory : Boolean,
   @SerializedName("can_set_category") var canSetCategory : Boolean,
   @SerializedName("category_action") var categoryAction : CategoryAction,
   @SerializedName("topic_id") var topicId : Long,
   @SerializedName("post_type") var postType : String,
   @SerializedName("text") var text : String,
   @SerializedName("marked_as_ads") var markedAsAds : Long,
   @SerializedName("attachments") var attachments : List<Attachments>,
   @SerializedName("post_source") var postSource : PostSource,
   @SerializedName("comments") var comments : Comments,
   @SerializedName("likes") var likes : Likes,
   @SerializedName("reposts") var reposts : Reposts,
   @SerializedName("post_id") var postId : Long,
   @SerializedName("type") var type : String

)