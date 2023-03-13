package com.example.a18_01_2023_googlemapsdemo

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.a18_01_2023_googlemapsdemo.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var bitcodeMarker : Marker
    private lateinit var shaniwarWadaMarker : Marker
    private lateinit var circle : Circle
    private lateinit var polygon : Polygon

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
        addShapes()
        onInfoWindowClickListener()
        onMarkerDragClickListener()
        setInfoWindowAdapter()
        miscellaneous()
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bitcodeMarker.position))
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
    private fun miscellaneous(){
        mMap.setOnPoiClickListener {
            Log.e("tag","${it.name} -- ${it.placeId} -- ${it.latLng}")
        }
    }
    private fun addShapes(){
        circle = mMap.addCircle(
            CircleOptions().center(LatLng(18.4,73.80))
                .radius(5000.0)
                .strokeColor(Color.RED)
                .strokeWidth(10.0F)
                .fillColor(Color.MAGENTA)
        )

        polygon = mMap.addPolygon(
            PolygonOptions()
                .add(LatLng(23.21,72.63))
                .add(LatLng(23.25,77.41))
                .add(LatLng(21.25,81.62))
                .add(LatLng(18.52,73.85))
                .strokeWidth(10.0F)
                .strokeColor(Color.CYAN)
                .fillColor(Color.argb(5,255,0,0))
        )
    }
    private fun onInfoWindowClickListener(){
        mMap.setOnInfoWindowClickListener {
            Log.e("tag","${it.title}")
            Toast.makeText(this,"${it.title} -- ${it.position.longitude} -- ${it.position.latitude}",Toast.LENGTH_LONG).show()
        }
    }

    private fun onMarkerDragClickListener(){
        mMap.setOnMarkerDragListener(
            object : GoogleMap.OnMarkerDragListener{
                override fun onMarkerDragStart(marker: Marker) {
                   Log.e("onStart","${marker.title} -- ${marker.position.latitude} -- ${marker.position.longitude}")
                }

                override fun onMarkerDrag(marker: Marker) {
                    Log.e("drag","${marker.title} -- ${marker.position.latitude} -- ${marker.position.longitude}")
                }

                override fun onMarkerDragEnd(marker: Marker) {
                    Log.e("onEnd","${marker.title} -- ${marker.position.latitude} -- ${marker.position.longitude}")
                }
            }
        )
    }

   /* private fun markerClickListener(){
        mMap.setOnMarkerClickListener {
           object : GoogleMap.OnMarkerClickListener{
               override fun onMarkerClick(p0: Marker): Boolean {
                   Log.e("tag","${p0.title}")
                    return true
               }
           }
        }
    }*/

    private fun setInfoWindowAdapter(){
        mMap.setInfoWindowAdapter(MyInfoWindowAdapter())
    }
    private inner class MyInfoWindowAdapter : GoogleMap.InfoWindowAdapter{
        override fun getInfoContents(p0: Marker): View? {
            var infoWindowView = layoutInflater.inflate(R.layout.my_info_window,null)
            var infoWindowBinding = com.example.a18_01_2023_googlemapsdemo.databinding.MyInfoWindowBinding.bind(infoWindowView)
            infoWindowBinding.txtName.text = p0.title
            infoWindowBinding.txtPosition.text = p0.position.toString()
            return infoWindowView
        }

        override fun getInfoWindow(p0: Marker): View? {
           return null
        }
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
       mMap.moveCamera(CameraUpdateFactory.newLatLng(bitcodeMarker.position))
        mMap.moveCamera(CameraUpdateFactory.zoomBy(2000.0F))



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