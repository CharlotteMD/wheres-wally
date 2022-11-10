package com.example.whereswally.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "my_location_table")
data class MyLocation(
    @PrimaryKey
    @ColumnInfo(name = "myLocation") val myLocation: String
)
