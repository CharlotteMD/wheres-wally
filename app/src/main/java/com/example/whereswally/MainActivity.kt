package com.example.whereswally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.whereswally.data.WordDao
import com.example.whereswally.data.WordRepository
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
    LocationScreen(
        LocationViewModel(LocationServices.getFusedLocationProviderClient(currentContext)),
        WordRepository(WordDao)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WheresWallyTheme {
        WheresWallyMainScreen()
    }
}