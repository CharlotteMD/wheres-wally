package com.example.whereswally.viewmodels

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.whereswally.data.MyLocationRepository
import com.google.android.gms.location.*
import kotlinx.coroutines.launch

class MyLocationData(
    val lat: Double = 0.0,
    val long: Double = 0.0,
    val speed: Float = 0.0F,
    val accuracy: Float = 0.0F,
    val altitude: Double = 0.0,
    val bearing: Float = 0.0F,
    val time: Long = 0
)

interface ILocationViewModel {
    var locationFromGps: MyLocationData?
    fun startTracking()
    fun stopTracking()
    val allLocations: LiveData<List<MyLocationData>>
}

class LocationViewModel(
        private val fusedLocationProviderClient: FusedLocationProviderClient,
        private val repository: MyLocationRepository
    ): ViewModel(), ILocationViewModel {

    override var locationFromGps: MyLocationData? by mutableStateOf(null)

    fun addLocation(myLocation: MyLocationData) =
        viewModelScope.launch {
            repository.addLocation(myLocation)
        }

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
                locationFromGps?.let { addLocation(myLocation = it) }
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

    override val allLocations: LiveData<List<MyLocationData>> = repository.allLocations.asLiveData()



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
