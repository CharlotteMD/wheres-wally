package com.example.whereswally.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class MyLocationRepository(private val wordDao: MyLocationDao) {

    val allWords: Flow<List<MyLocation>> = wordDao.getAllLocations()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addLocation(myLocation: MyLocation) {
        wordDao.addLocation(myLocation)
    }

}