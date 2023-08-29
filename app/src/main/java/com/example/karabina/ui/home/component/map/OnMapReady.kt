package com.example.karabina.ui.home.component.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

fun OnMapReady(googleMap: GoogleMap, place: LatLng, imageUrl: String) {
    googleMap.addMarker(
        MarkerOptions()
            .position(place)
            .icon(BitmapDescriptorFactory.fromFile(imageUrl))
            .title("title")
    )
    googleMap.moveCamera(CameraUpdateFactory.newLatLng(place))
}
