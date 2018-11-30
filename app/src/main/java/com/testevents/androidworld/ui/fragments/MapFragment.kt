package com.testevents.androidworld.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.testevents.androidworld.R
import com.testevents.androidworld.ui.activities.MainActivity

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private var latitude: String = ""
    private var longitude: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        val bundle = arguments
        if (bundle != null) {
            latitude = bundle.getString("latitude", "")
            longitude = bundle.getString("longitude", "")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).fullScreenMode(true)
        (activity as MainActivity).setActionBarTitle(getString(R.string.app_name))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        showCityOnMap(LatLng(latitude.toDouble(), longitude.toDouble()))
    }

    private fun showCityOnMap(cityLatLng: LatLng) {
        mMap.addMarker(MarkerOptions().position(cityLatLng))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLatLng, 8f))
    }
}
