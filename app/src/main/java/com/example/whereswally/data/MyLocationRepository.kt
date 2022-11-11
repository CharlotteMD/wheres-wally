package com.example.whereswally.data

import androidx.annotation.WorkerThread
import com.example.whereswally.viewmodels.MyLocationData
import kotlinx.coroutines.flow.Flow

class MyLocationRepository(private val myLocationDao: MyLocationDao) {

    val allLocations: Flow<List<MyLocationData>> = myLocationDao.getAllLocations()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addLocation(myLocation: MyLocationData) {
        myLocationDao.addLocation(myLocation)
    }

}