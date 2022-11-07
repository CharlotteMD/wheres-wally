package com.example.whereswally.viewmodels

import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.location.LocationRequestCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationResult.create
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY

class MyLocation(val lat: Double, val long: Double) {

}

interface ILocationViewModel {
    var locationFromGps: MyLocation?
    fun startTracking()
}

class LocationViewModel(private val fusedLocationProviderClient: FusedLocationProviderClient): ViewModel(), ILocationViewModel {

    override var locationFromGps: MyLocation? by mutableStateOf(null)

    private val locationCallback =
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                Log.d(
                    "onLocationResult",
                    "locationResult.latitude: ${locationResult.lastLocation?.latitude}"
                )
                locationFromGps = locationResult.lastLocation?.let { l -> MyLocation(l.latitude, l.longitude) }
            }
        }

    override fun startTracking() {

        val locationRequest = LocationRequest
            .Builder( 10000 )
            .build()

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())


    }



}