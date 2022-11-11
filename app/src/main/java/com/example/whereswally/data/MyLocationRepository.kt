package com.example.whereswally.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class MyLocationRepository(private val myLocationDao: MyLocationDao) {

    val allLocations: Flow<List<MyLocation>> = myLocationDao.getAllLocations()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addLocation(myLocation: MyLocation) {
        myLocationDao.addLocation(myLocation)
    }

}