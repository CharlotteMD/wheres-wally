package com.example.whereswally.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.whereswally.viewmodels.MyLocationData
import kotlinx.coroutines.flow.Flow

@Dao
interface MyLocationDao {

    @Query("SELECT * FROM my_location_table")
    fun getAllLocations(): Flow<List<MyLocationData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addLocation(myLocation: MyLocationData)

    @Update
    fun updateLocation(myLocation: MyLocationData)

    @Delete
    fun deleteLocation(myLocation: MyLocationData)

}
