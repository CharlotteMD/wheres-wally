package com.example.whereswally.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.whereswally.viewmodels.MyLocationData


@Database(entities = arrayOf(MyLocationData::class), version = 1, exportSchema = false)
abstract class MyLocationDatabase : RoomDatabase() {

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
