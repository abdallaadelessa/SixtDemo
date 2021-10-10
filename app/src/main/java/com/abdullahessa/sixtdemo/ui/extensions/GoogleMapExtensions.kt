package com.abdullahessa.sixtdemo.ui.extensions

import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.model.LatLngBounds
import com.google.android.libraries.maps.model.Marker

/**
 * @author Created by Abdullah Essa on 10.10.21.
 */
fun GoogleMap.moveCameraToCenter(markers: List<Marker>) {
    if (markers.isEmpty()) return

    val builder = LatLngBounds.Builder()
    for (marker in markers) {
        builder.include(marker.position)
    }
    val bounds = builder.build()

    animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0))
}
