package com.example.whereswally.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

@Dao
interface MyLocationDao {

    @Query("SELECT * FROM my_location_table")
    fun getAllLocations(): Flow<List<MyLocation>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addLocation(myLocation: MyLocation)

    @Update
    fun updateLocation(myLocation: MyLocation)

    @Delete
    fun deleteLocation(myLocation: MyLocation)

}
