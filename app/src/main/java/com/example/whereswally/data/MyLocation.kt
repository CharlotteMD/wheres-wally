package com.example.whereswally.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_location_table")
data class MyLocation(
    @PrimaryKey

    @ColumnInfo(name = "myLocation")
    val myLocation: String,

    @ColumnInfo(name = "lat")
    var lat: Double? = 0.0,

    @ColumnInfo(name = "long")
    var long: Double? = null,

    @ColumnInfo(name = "speed")
    var speed: Float = 0.0F,

    @ColumnInfo(name = "accuracy")
    var accuracy: Float = 0.0F,

    @ColumnInfo(name = "altitude")
    var altitude: Double = 0.0,

    @ColumnInfo(name = "bearing")
    var bearing: Float = 0.0F,

    @ColumnInfo(name = "time")
    var time: Long = 0,
)
