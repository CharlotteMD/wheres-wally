package com.example.whereswally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.whereswally.data.MyLocationDao
import com.example.whereswally.data.MyLocationDatabase
import com.example.whereswally.data.MyLocationRepository
import com.example.whereswally.ui.theme.WheresWallyTheme
import com.example.whereswally.viewmodels.LocationViewModel
import com.example.whereswally.views.LocationScreen
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            WheresWallyTheme {
                WheresWallyMainScreen()
            }
        }
    }
}

@Composable
fun WheresWallyMainScreen() {
    val currentContext = LocalContext.current
    val db = MyLocationDatabase.getDatabase(context = currentContext)

    LocationScreen(
        LocationViewModel(
            LocationServices.getFusedLocationProviderClient(currentContext),
            MyLocationRepository(db.myLocationDao())
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WheresWallyTheme {
        WheresWallyMainScreen()
    }
}