package com.example.whereswally.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.example.whereswally.data.MyLocation
import com.example.whereswally.viewmodels.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationScreen(
    locationViewModel: ILocationViewModel
) {

    val fineLocationPermission =
        rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    val backgroundLocationPermission =
        rememberPermissionState(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Where's Wally?",
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))

        when (fineLocationPermission.status) {
            PermissionStatus.Granted -> {
                when (fineLocationPermission.status) {
                    PermissionStatus.Granted -> {
                        when (backgroundLocationPermission.status) {
                            PermissionStatus.Granted -> {
                                DisplayIfPermissionGranted(locationViewModel = locationViewModel)
                            }
                            else -> {
                                DisplayIfPermissionDenied(backgroundLocationPermission)
                            }
                        }

                    }
                    else -> {
                        DisplayIfPermissionDenied(fineLocationPermission)
                    }
                }

            }

        }
    }
}

@Composable
fun DisplayIfPermissionGranted(
    locationViewModel: ILocationViewModel
) {
    var trackingOnOffState by remember { mutableStateOf(true) }

    Row() {
        Button(
            onClick = {
                locationViewModel.startTracking()
            },
        ){
            Text(text = "Start Tracking")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                locationViewModel.stopTracking()
            },
        ){
            Text(text = "Stop Tracking")
        }
    }
    DisplayLocationData(locationViewModel)

    Row() {
        Text(text = "Power Saving mode ")
        Switch(
            checked = trackingOnOffState,
            onCheckedChange = { trackingOnOffState = it }
        )
        Text(text = "High accuracy: ")
    }

    WordTestScreen(locationViewModel = locationViewModel)
}

@Composable
fun WordTestScreen(
    locationViewModel: ILocationViewModel
) {

//    val words = locationViewModel.allWords
//
//    words.forEach { word ->
//        Text(word)
//    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DisplayIfPermissionDenied(
    permission: PermissionState
) {
    Text(text = "You must give permission to use this app.")
    Button(

    //        TODO - FIX
    //        Not confident this actually works
    //        Intended behaviour - launches Android permissions dialog
    //        Perhaps put info of how to update in your phone settings

        onClick = {permission.launchPermissionRequest()}
    ){
        Text(text = "Give permission")
    }

//    WordTestScreen()
}

@Composable
fun DisplayLocationData(
    locationViewModel: ILocationViewModel
) {

    var latInput = locationViewModel.locationFromGps?.lat ?: 0.0
    var longInput = locationViewModel.locationFromGps?.long ?: 0.0
    var speedInput = locationViewModel.locationFromGps?.speed ?: 0.0F
    var accuracyInput = locationViewModel.locationFromGps?.accuracy ?: 0.0F
    var altitudeInput = locationViewModel.locationFromGps?.altitude ?: 0.0
    var bearingInput = locationViewModel.locationFromGps?.bearing ?: 0.0F
    var timeInput = locationViewModel.locationFromGps?.time ?: 0.0

    var latList = listOf<Double>()

    fun addToLatList(latInput: Double): List<Double> {
        latList = latList + latInput
        return latList
    }

    var latMutableInput by remember { mutableStateOf(0.0) }
    addToLatList(latMutableInput)

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Latitude:  " + latInput)
        Text(text = "Longitude:  " + longInput)
        Text(text = "Altitude:  " + altitudeInput)
        Text(text = "Bearing:  " + bearingInput)
        Text("Accuracy: " + accuracyInput)
        Text("Speed: " + speedInput)
        Text(text = "Time:  " + timeInput)

        Spacer(modifier = Modifier.height(8.dp))
        latList.forEach { lat ->
            Text(text = "lat: " + lat)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LocationScreen(
        locationViewModel = object : ILocationViewModel{
            override fun startTracking() {
                TODO("Not yet implemented")
            }
            override var locationFromGps: MyLocationData? = MyLocationData(
                12.5,
                16.03,
                12.1F,
                45.6F,
                1500.8,
                2.5F,
                1000001234567
            )
            override fun stopTracking() {
                TODO("Not yet implemented")
            }
            override val allLocations: LiveData<List<MyLocation>>
                get() {
                    TODO()
                }
            override fun addLocation(myLocation: MyLocation) {
                TODO("Not yet implemented")
            }
        }
    )
}
