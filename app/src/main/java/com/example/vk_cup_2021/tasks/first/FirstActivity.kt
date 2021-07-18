package com.example.vk_cup_2021.tasks.first

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.example.GoogleDirections
import com.example.vk_cup_2021.R
import com.example.vk_cup_2021.modules.Notifier
import com.example.vk_cup_2021.modules.first_task.MapWorker
import com.example.vk_cup_2021.retrofit.first_task.api.GoogleMapsAPI
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_first.*
import kotlinx.android.synthetic.main.fragment_modal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import kotlin.random.Random


class FirstActivity : AppCompatActivity(), OnMapReadyCallback
    ,GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerDragListener{

    private val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    private val COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION

    private var origin: String? = null
    private var destination: MarkerOptions? = null
    private lateinit var mMap: GoogleMap
    private lateinit var retrofit: Retrofit
    private lateinit var api: GoogleMapsAPI
    private lateinit var geocoder: Geocoder

    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private var mLocationPermissionsGranted: Boolean = false
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<View>

    private var polyline: Polyline? = null

    private var whiteColor: Int = 0
    private var purpleColor: Int = 0

    private var newPrice1: Long = 0
    private var newPrice2: Long = 0
    private var newPrice3: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        getLocationPermissions()

        retrofit = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(GoogleMapsAPI::class.java)

        taxis.visibility = View.GONE
        buy_taxi.visibility = View.GONE

        mBottomSheetBehavior = BottomSheetBehavior.from(modal)

        whiteColor = resources.getColor(R.color.white)
        purpleColor = resources.getColor(R.color.vk_purple_trasnparent)

        geocoder = Geocoder(this)
        setListeners()
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            1234 -> {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    // permission not granted
                }
                else {
                    // permission granted
                    mLocationPermissionsGranted = true
                    initMap()
                }
            }
        }
    }

    fun setListeners(){
        from_point.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                onWordChangedAction()
            }
        })

        to_point.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                onWordChangedAction()
            }
        })

        taxi1.setOnClickListener() {
            taxi1.setBackgroundColor(purpleColor)
            taxi2.setBackgroundColor(whiteColor)
            taxi3.setBackgroundColor(whiteColor)

            buy_taxi.text = resources.getString(R.string.buy_for, newPrice1)
        }

        taxi2.setOnClickListener() {
            taxi1.setBackgroundColor(whiteColor)
            taxi2.setBackgroundColor(purpleColor)
            taxi3.setBackgroundColor(whiteColor)

            buy_taxi.text = resources.getString(R.string.buy_for, newPrice2)
        }

        taxi3.setOnClickListener() {
            taxi1.setBackgroundColor(whiteColor)
            taxi2.setBackgroundColor(whiteColor)
            taxi3.setBackgroundColor(purpleColor)

            buy_taxi.text = resources.getString(R.string.buy_for, newPrice3)
        }
    }

    fun buyTaxi(v: View){
        Notifier.showToast(this, "Заказано. Счастливого пути!")
    }

    fun onWordChangedAction(){
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        taxis.visibility = View.GONE
        ready.visibility = View.VISIBLE
        buy_taxi.visibility = View.GONE
    }

    fun hideKeyboard(){
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    fun initMap(){
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    fun getLocationPermissions(){
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)

        if (ContextCompat.checkSelfPermission(this.applicationContext,
                        FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.applicationContext,
                            COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true
                initMap()
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        1234)
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    1234)
        }
    }

    fun getDeviceLocation(action: (ll: LatLng?) -> Unit){
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        try {
            var result: LatLng? = null

            if (mLocationPermissionsGranted){
                var location = mFusedLocationProviderClient.lastLocation

                location.addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful){
                        var loc = it.result
                        if (loc != null){
                            var ll = LatLng(loc.latitude, loc.longitude)
                            action.invoke(ll)
                        }
                        mMap.isMyLocationEnabled = true
                    } else {
                        Notifier.showToast(this, "Не удалось получить ваше местоположение")
                    }
                })

            }
        } catch (e: SecurityException){

        }
    }

    fun processPath(body: GoogleDirections?){
        mMap.clear()
        if (polyline != null) {
            polyline?.remove()
        }

        val polylineOptions = PolylineOptions()

        val routes = body?.routes
        val legsList = routes?.get(0)?.legs
        val legs = legsList?.get(0)

        val s = legs?.startLocation
        var ll = LatLng(s?.lat!!, s.lng)
        polylineOptions.add(ll)
        mMap.addMarker(MarkerOptions().position(ll).title(""))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.locataion))

        val e = legs?.endLocation
        ll = LatLng(e?.lat!!, e.lng)

        destination = MarkerOptions().position(ll).title("").draggable(true)
        mMap.addMarker(destination)
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin))

        var path = MapWorker.decodePoly(body?.routes?.get(0)?.overviewPolyline.points)
        polylineOptions.addAll(path)
        polylineOptions.color(R.color.vk_purple)
        polylineOptions.width(14f)

        polyline = mMap.addPolyline(polylineOptions)

        val b = body?.routes?.get(0)?.bounds
        val bounds = LatLngBounds(
            LatLng((b.southwest.lat), b.southwest.lng),  // SW bounds
            LatLng(b.northeast.lat, b.northeast.lng) // NE bounds
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0))
    }

    fun countPricesAndTime(body: GoogleDirections?){
        newPrice1 = MapWorker.countPrice(body, MapWorker.FAST_TAXI)
        newPrice2 = MapWorker.countPrice(body, MapWorker.ATTRACTION_TAXI)
        newPrice3 = MapWorker.countPrice(body, MapWorker.DRONE_TAXI)

        var time = body!!.routes.get(0).legs.get(0).duration.value
        val time1 = MapWorker.countStatusTimeInSec(time, MapWorker.FAST_TAXI)
        val time2 = MapWorker.countStatusTimeInSec(time, MapWorker.ATTRACTION_TAXI)
        val time3 = MapWorker.countStatusTimeInSec(time, MapWorker.DRONE_TAXI)

        val wait1 = (Random.nextInt(5, 13) * MapWorker.FAST_TAXI).toInt()
        val wait2 = (Random.nextInt(5, 13) * MapWorker.ATTRACTION_TAXI).toInt()
        val wait3 = (Random.nextInt(5, 13) * MapWorker.DRONE_TAXI).toInt()

        val bottom1 = resources.getString(R.string.timeInWay, "$time1") + " | " +
                resources.getString(R.string.timeAwait, "$wait1 мин")
        val bottom2 = resources.getString(R.string.timeInWay, "$time2") + " | " +
                resources.getString(R.string.timeAwait, "$wait2 мин")
        val bottom3 = resources.getString(R.string.timeInWay, "$time3") + " | " +
                resources.getString(R.string.timeAwait, "$wait3 мин")

        time_waiting1.text = bottom1
        time_waiting2.text = bottom2
        time_waiting3.text = bottom3

        price1.text = resources.getString(R.string.ruble, newPrice1)
        price2.text = resources.getString(R.string.ruble, newPrice2)
        price3.text = resources.getString(R.string.ruble, newPrice3)
    }

    fun sendRequestForPath(origin: String, dest: String, key: String){
        this@FirstActivity.origin = origin
        var call = api.getDirections(origin, dest, key, "driving", "RU")
        call.enqueue(object: Callback<GoogleDirections>{
            override fun onFailure(call: Call<GoogleDirections>, t: Throwable) {

            }

            override fun onResponse(call: Call<GoogleDirections>, response: Response<GoogleDirections>) {
                val body = response.body()

                if (!MapWorker.isValid(body)) {
                    Notifier.showToast(this@FirstActivity, "Нет результатов. Попробуйте уточнить места. Например, введите название города")
                    return
                }

                processPath(body)
                countPricesAndTime(body)

                taxis.visibility = View.VISIBLE
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED)
                ready.visibility = View.GONE
                buy_taxi.visibility = View.VISIBLE

                hideKeyboard()
                taxi1.performClick()
            }
        })
    }

    fun findPath(v: View){
        var origin = from_point.text.toString()
        var dest = to_point.text.toString()
        var key = resources.getString(R.string.google_api)

        if (origin.isEmpty()) {
            getDeviceLocation() {
                if (it != null){
                    origin = "${it.latitude},${it.longitude}"
                    sendRequestForPath(origin, dest, key)
                }
            }
        } else
            sendRequestForPath(origin, dest, key)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerDragListener(this)

        if (mLocationPermissionsGranted){
            getDeviceLocation() {
                if (it != null) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(it))

                    origin = "${it.latitude},${it.longitude}"
                    destination = MarkerOptions().position(it).title("").draggable(true)
                    mMap.addMarker(destination)
                            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin))
                }
            }
        }
    }

    override fun onMapLongClick(ll: LatLng?) {

    }

    override fun onMarkerDragEnd(m: Marker?) {
        try {
            var ll = m?.position
            var addresses = geocoder.getFromLocation(ll?.latitude!!, ll?.longitude!!, 1)
            if (addresses.size > 0){
                var addr = addresses.get(0)
                var street = addr.getAddressLine(0)
                var key = resources.getString(R.string.google_api)
                to_point.setText(street)

                sendRequestForPath(origin!!, street, key)
                hideKeyboard()
                taxi1.performClick()
            }
        } catch (e: IOException){

        }
    }

    override fun onMarkerDragStart(p0: Marker?) {

    }

    override fun onMarkerDrag(p0: Marker?) {

    }

}