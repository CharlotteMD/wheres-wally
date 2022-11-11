package com.example.whereswally.viewmodels

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.whereswally.data.MyLocation
import com.example.whereswally.data.MyLocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyLocationData(
    val lat: Double,
    val long: Double,
    val speed: Float,
    val accuracy: Float,
    val altitude: Double,
    val bearing: Float,
    val time: Long
)

interface ILocationViewModel {
    var locationFromGps: MyLocationData?
    fun startTracking()
    fun stopTracking()
    val allLocations: LiveData<List<MyLocation>>
}

class LocationViewModel(
        private val fusedLocationProviderClient: FusedLocationProviderClient,
        private val repository: MyLocationRepository
    ): ViewModel(), ILocationViewModel {

    override var locationFromGps: MyLocationData? by mutableStateOf(null)

    private val locationCallback =
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationFromGps = locationResult.lastLocation?.let { l ->
                    MyLocationData(
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

    // get values of locationFromGps
    // add them to Room via ViewModel
    // only make data visible to activity - data is updated via Room

    // TO FIX
    // find a way to access locationFromGps values - they are mutable maybe change them into a Flow?
    // add extra columns to

    }

    override fun stopTracking() {
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    // ROOM

    override val allLocations: LiveData<List<MyLocation>> = repository.allLocations.asLiveData()

//    override fun addLocation(myLocation: MyLocation): Job =
//        viewModelScope.launch {
//            repository.addLocation(myLocation) }
//

    }


class LocationViewModelFactory(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val repository: MyLocationRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LocationViewModel(fusedLocationProviderClient, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
