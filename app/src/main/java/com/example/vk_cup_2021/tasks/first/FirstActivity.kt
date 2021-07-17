package com.example.vk_cup_2021.tasks.first

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.example.GoogleDirections
import com.example.example.StartLocation
import com.example.vk_cup_2021.R
import com.example.vk_cup_2021.retrofit.first_task.api.GoogleMapsAPI
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_modal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FirstActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var retrofit: Retrofit
    private lateinit var api: GoogleMapsAPI

    private var polyline: Polyline? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        retrofit = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(GoogleMapsAPI::class.java)

        taxis.visibility = View.GONE
        buy_taxi.visibility = View.GONE
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            1 -> {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    // permission not granted

                }
                else {
                    // permission granted

                }
            }
        }
    }

    fun findPath(v: View){
        var origin = from_point.text.toString()
        var dest = to_point.text.toString()
        var key = resources.getString(R.string.google_api)
        var call = api.getDirections(origin, dest, key, "driving", "RU")
        call.enqueue(object: Callback<GoogleDirections>{
            override fun onFailure(call: Call<GoogleDirections>, t: Throwable) {

            }

            override fun onResponse(call: Call<GoogleDirections>, response: Response<GoogleDirections>) {
                val body = response.body()

                if (polyline != null) {
                    polyline?.remove()
                    mMap.clear()
                }

                val polylineOptions = PolylineOptions()

                val legs = body?.routes?.get(0)?.legs?.get(0)
                val steps = legs?.steps

                val s = legs?.startLocation
                var ll = LatLng(s?.lat!!, s.lng)
                polylineOptions.add(ll)
                mMap.addMarker(MarkerOptions().position(ll).title("Marker in Sydney"))
                        .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.locataion))

                val e = legs?.endLocation
                ll = LatLng(e?.lat!!, e.lng)
                mMap.addMarker(MarkerOptions().position(ll).title("Marker in Sydney"))
                        .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin))

                if (steps != null) {
                    for (i in 0 until steps.size) {
                        val end = steps.get(i).endLocation
                        val latitude = end.lat
                        val longitude = end.lng
                        polylineOptions.color(R.color.vk_purple)
                        polylineOptions.width(14f)
                        polylineOptions.add(LatLng(latitude, longitude))
                    }
                }

                polyline = mMap.addPolyline(polylineOptions)

                val b = body?.routes?.get(0)?.bounds
                val bounds = LatLngBounds(
                        LatLng((b.southwest.lat), b.southwest.lng),  // SW bounds
                        LatLng(b.northeast.lat, b.northeast.lng) // NE bounds
                )
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0))
            }

        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    ) !== PackageManager.PERMISSION_GRANTED
                    &&
                    ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    ) !== PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                        arrayOf(
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        ), 1
                )
                //return;
            } else {
                googleMap.isMyLocationEnabled = true
                mMap = googleMap

                // Add a marker in Sydney and move the camera
                val sydney = LatLng(-34.0, 151.0)
                mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            }
        }
    }
}