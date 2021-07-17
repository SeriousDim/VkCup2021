package com.example.example

import com.google.gson.annotations.SerializedName

   
data class GeocodedWaypoints (

   @SerializedName("geocoder_status") var geocoderStatus : String,
   @SerializedName("place_id") var placeId : String,
   @SerializedName("types") var types : List<String>

)