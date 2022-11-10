package com.example.whereswally.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

class MyLocationDatabase {

    @Database(entities = arrayOf(MyLocation::class), version = 1, exportSchema = false)
    public abstract class MyLocationDatabase : RoomDatabase() {

        abstract fun myLocationDao(): MyLocationDao

        companion object {
            @Volatile
            private var INSTANCE: MyLocationDatabase? = null

            fun getDatabase(context: Context): MyLocationDatabase {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyLocationDatabase::class.java,
                        "my_location_database"
                    ).build()
                    INSTANCE = instance
                    instance
                }
            }
        }
    }
}