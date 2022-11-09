package com.example.whereswally.viewmodels

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority

class MyLocation(
    val lat: Double,
    val long: Double,
    val speed: Float,
    val accuracy: Float,
    val altitude: Double,
    val bearing: Float,
    val time: Long
)

interface ILocationViewModel {
    var locationFromGps: MyLocation?
    fun startTracking()
    fun stopTracking()
}

class LocationViewModel(
        private val fusedLocationProviderClient: FusedLocationProviderClient,
    ): ViewModel(), ILocationViewModel {

    override var locationFromGps: MyLocation? by mutableStateOf(null)

    private val locationCallback =
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationFromGps = locationResult.lastLocation?.let { l ->
                    MyLocation(
                        l.latitude,
                        l.longitude,
                        l.speed,
                        l.accuracy,
                        l.altitude,
                        l.bearing,
                        l.time
                    )
                }
            }
        }

        @RequiresPermission(anyOf = [ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION])
        override fun startTracking() {

            val locationRequest = LocationRequest
                .Builder(1000)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build()

            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

        }

        override fun stopTracking() {
            stopLocationUpdates()
        }

        private fun stopLocationUpdates() {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }
