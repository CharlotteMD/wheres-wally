package com.example.whereswally.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

class WordRoomDatabase {

    @Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
    public abstract class WordRoomDatabase : RoomDatabase() {

        abstract fun wordDao(): WordDao

        companion object {
            @Volatile
            private var INSTANCE: WordRoomDatabase? = null

            fun getDatabase(context: Context): WordRoomDatabase {
                // if the INSTANCE is not null, then return it,
                // if it is, then create the database
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        WordRoomDatabase::class.java,
                        "word_database"
                    ).build()
                    INSTANCE = instance
                    instance
                }
            }
        }
    }
}