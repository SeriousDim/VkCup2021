package com.example.vk_cup_2021.modules.first_task

import com.example.example.GoogleDirections
import com.example.vk_cup_2021.modules.Notifier
import com.google.android.gms.maps.model.LatLng
import java.util.*
import kotlin.collections.ArrayList

class MapWorker{

    companion object{

        val FAST_TAXI = 1.0
        val ATTRACTION_TAXI = 1.18
        val DRONE_TAXI = 1.35

        private val minPrice = 60
        private val timeConst = 16
        private val dayConst = 11
        private val distanceCoef = 7.9
        private val dayCoefs = doubleArrayOf(1.0, 1.6, 1.5, 1.2, 1.0, 1.2, 1.4, 1.7)
        private val timeCoefs = doubleArrayOf(1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 2.2, 2.3, 2.4, 2.8, 2.9,
            3.0, 3.1, 3.0, 2.7, 2.6, 2.8, 3.0, 2.6, 2.5, 2.1, 2.0, 1.8)

        fun isValid(body: GoogleDirections?): Boolean{
            val routes = body?.routes
            if (routes == null || routes.size == 0)
                return false

            val legsList = routes.get(0).legs

            if (legsList.size == 0)
                return false

            return true
        }

        fun countStatusTimeInSec(value: Int, status: Double): String{
            var newSecs = value * status
            var mins = newSecs / 60
            var hours = mins / 60

            if (hours >= 1.0)
                return "${Math.floor(hours).toInt()} ч ${Math.floor(mins - hours.toInt() * 60).toInt()} мин"
            return "${Math.floor(mins).toInt()} мин"
        }

        fun countPrice(directions: GoogleDirections?, status: Double): Long{
            var legs = directions?.routes?.get(0)?.legs?.get(0)
            var distanceMeters = legs?.distance?.value

            var date = Date()
            val calendar = GregorianCalendar.getInstance()
            calendar.time = date
            var t = timeCoefs[calendar.get(Calendar.HOUR_OF_DAY)]
            var d = dayCoefs[calendar.get(Calendar.DAY_OF_WEEK)]

            return Math.round((minPrice + timeConst * t + dayConst * d + distanceMeters!! / 1000.0 * distanceCoef) * status)
        }

        fun decodePoly(encoded: String): List<LatLng>? {
            val poly: MutableList<LatLng> = ArrayList()
            var index = 0
            val len = encoded.length
            var lat = 0
            var lng = 0
            while (index < len) {
                var b: Int
                var shift = 0
                var result = 0
                do {
                    b = encoded[index++].toInt() - 63
                    result = result or (b and 0x1f shl shift)
                    shift += 5
                } while (b >= 0x20)
                val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
                lat += dlat
                shift = 0
                result = 0
                do {
                    b = encoded[index++].toInt() - 63
                    result = result or (b and 0x1f shl shift)
                    shift += 5
                } while (b >= 0x20)
                val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
                lng += dlng
                val p = LatLng(lat.toDouble() / 1E5,
                        lng.toDouble() / 1E5)
                poly.add(p)
            }
            return poly
        }

    }

}