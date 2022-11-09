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
import com.example.whereswally.viewmodels.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationScreen(
    locationViewModel: ILocationViewModel
) {

    val fineLocationPermission = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    var trackingOnOffState by remember { mutableStateOf(true) }

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
                    Text(text = "High accuracy: ")
                    Switch(
                        checked = trackingOnOffState,
                        onCheckedChange = { trackingOnOffState = it }
                    )
                    Text(text = "Power Saving mode ")
                }
            }
            else -> {
                Text(text = "You must give permission to use this app.")
                Button(
                    onClick = {fineLocationPermission.launchPermissionRequest()}
                ){
                    Text(text = "Give permission")
                }
            }
        }

    }


}

@Composable
fun DisplayLocationData(
    locationViewModel: ILocationViewModel
) {

    var latInput = locationViewModel.locationFromGps?.lat ?: 0.0
    var longInput = locationViewModel.locationFromGps?.long ?: 0.0
    var speedInput = locationViewModel.locationFromGps?.speed ?: 0.0F

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Lat:  " + latInput)
        Text(text = "Lon:  " + longInput)
        Text("Speed: " + speedInput)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LocationScreen(
        locationViewModel = object : ILocationViewModel{
            override fun startTracking() {
                TODO("Not yet implemented")
            }
            override var locationFromGps: MyLocation? = MyLocation(12.5, 16.03, 12.1F)
            override fun stopTracking() {
                TODO("Not yet implemented")
            }
        }
    )
}
