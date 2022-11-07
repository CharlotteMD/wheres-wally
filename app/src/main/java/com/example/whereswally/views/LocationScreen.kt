package com.example.whereswally.views

import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whereswally.EditLocation
import com.example.whereswally.TextLocationInfo
import com.example.whereswally.WheresWallyLocationLogic
import com.example.whereswally.ui.theme.WheresWallyTheme
import com.example.whereswally.viewmodels.ILocationViewModel
import com.example.whereswally.viewmodels.LocationViewModel
import com.example.whereswally.viewmodels.MyLocation


@Composable
fun LocationScreen(
    viewModel: ILocationViewModel
) {
    var latInput = viewModel.locationFromGps?.lat ?: 0.0
    var longInput = viewModel.locationFromGps?.long ?: 0.0

    val updateSwitchState = remember { mutableStateOf(true) }
    val gpsSwitchState = remember { mutableStateOf(true) }

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

        TextLocationInfo(
            latValue = latInput,
            lonValue = longInput,
            speed = 15.9,
        )

        Row() {
            Text(text = "Location Updates: ")
            Switch(
                checked = updateSwitchState.value,
                onCheckedChange = { updateSwitchState.value = it }
            )
        }

        Spacer(Modifier.height(16.dp))

        Row() {
            Text(text = "Save Power mode: ")
            Switch(
                checked = gpsSwitchState.value,
                onCheckedChange = { gpsSwitchState.value = it }
            )
        }

        Button(onClick = { viewModel.startTracking() }) {
            Text("Button")
        }

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LocationScreen(viewModel = object : ILocationViewModel{
        override fun startTracking() {
            TODO("Not yet implemented")
        }

        override var locationFromGps: MyLocation? = MyLocation(12.5, 16.03)

    })
}

//LocationViewModel(LocationServices.getFusedLocationProviderClient(LocalContext.current))