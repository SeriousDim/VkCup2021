package com.example.example

import com.google.gson.annotations.SerializedName

   
data class Routes (

   @SerializedName("bounds") var bounds : Bounds,
   @SerializedName("copyrights") var copyrights : String,
   @SerializedName("legs") var legs : List<Legs>,
   @SerializedName("overview_polyline") var overviewPolyline : OverviewPolyline,
   @SerializedName("summary") var summary : String,
   @SerializedName("warnings") var warnings : List<String>,
   @SerializedName("waypoint_order") var waypointOrder : List<String>

)