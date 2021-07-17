package com.example.example

import com.google.gson.annotations.SerializedName

   
data class GoogleDirections (

   @SerializedName("geocoded_waypoints") var geocodedWaypoints : List<GeocodedWaypoints>,
   @SerializedName("routes") var routes : List<Routes>,
   @SerializedName("status") var status : String

)