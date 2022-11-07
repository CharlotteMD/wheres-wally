package com.example.whereswally

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import com.example.whereswally.ui.theme.WheresWallyTheme
import com.example.whereswally.viewmodels.LocationViewModel
import com.example.whereswally.views.LocationScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationRequest
import java.util.jar.Manifest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            WheresWallyTheme {
                // A surface container using the 'background' color from the theme
                WheresWallyLocationLogic()
            }
        }
    }
}



@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WheresWallyLocationLogic(

) {

//    val locationPermissionsState = rememberMultiplePermissionsState(
//        listOf(
//            android.Manifest.permission.ACCESS_COARSE_LOCATION,
//            android.Manifest.permission.ACCESS_FINE_LOCATION,
//        )
//    )



    LocationScreen(LocationViewModel(LocationServices.getFusedLocationProviderClient(LocalContext.current)))
}



fun getGpsState(
    gpsSwitchState: MutableState<Boolean>
) {
//    if (gpsSwitchState) {
//    //        GPS is turned on - 16.44 video - based on priority/accuracy
//
//    }
}

@Composable
fun EditLocation(
    value: String,
    onValueChanged: (String) -> Unit,
    label: String,
) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun TextLocationInfo(
    latValue: Double,
    lonValue: Double,
    speed: Double,
) {
    Text(text = "Lat:  " + latValue)
    Text(text = "Lon:  " + lonValue)
    Text(text = "Altitude:  " + 0)
    Text(text = "Accuracy:  " + 10)
    Text("Speed: " + speed)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WheresWallyTheme {
        WheresWallyLocationLogic()
    }
}