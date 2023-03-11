package com.example.a18_01_2023_googlemapsdemo

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.a18_01_2023_googlemapsdemo.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var bitcodeMarker : Marker
    private lateinit var shaniwarWadaMarker : Marker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        initMapSettings()
        addMarkers()

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
    private fun addMarkers(){
        var markerOptionsForBitcodeMarker = MarkerOptions().title("Bitcode")
            .snippet("Bitcode Office")
            .position(LatLng(18.4,73.80))
            .visible(true)
            .rotation(45.0F)
            .draggable(true)
            .anchor(0.1F,0.1F)
            .zIndex(-90F)
            .alpha(0.8F)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
        bitcodeMarker = mMap.addMarker(markerOptionsForBitcodeMarker)!!

        var scaledImage = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.shaniwarwada),
            100,
            100,
            false
        )

        var shaniwarwadaIcon = BitmapDescriptorFactory.fromBitmap(scaledImage)
        var markerOptionsForShaniwarWada = MarkerOptions().title("Shaniwar Wada")
            .snippet("Historical Place")
            .icon(shaniwarwadaIcon)
            .visible(true)
            .position(LatLng(18.51,73.85))
        shaniwarWadaMarker = mMap.addMarker(markerOptionsForShaniwarWada)!!

    }

    @SuppressLint("MissingPermission")
    private fun initMapSettings(){
        mMap.isMyLocationEnabled = true
        mMap.isIndoorEnabled = true
        mMap.isTrafficEnabled = true
        mMap.isBuildingsEnabled = true
        mMap.uiSettings.isScrollGesturesEnabled = true
        mMap.uiSettings.isZoomGesturesEnabled = true
        mMap.uiSettings.isRotateGesturesEnabled = true
        mMap.uiSettings.isTiltGesturesEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isRotateGesturesEnabled = true
        mMap.uiSettings.isScrollGesturesEnabledDuringRotateOrZoom = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
    }
}