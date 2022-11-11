package com.example.whereswally.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MyLocationDatabaseTest {

    private lateinit var myLocationDao: MyLocationDao
    private lateinit var db: MyLocationDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, MyLocationDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        myLocationDao = db.myLocationDao()
    }

    @After
    @Throws(IOException::class)
    fun deleteDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    suspend fun insertAndGetTodo() = runBlocking {
        val locationTestValue = "Test"
        val locationItem = MyLocation(locationTestValue)
        myLocationDao.addLocation(locationItem)
        val oneItem = myLocationDao.getAllLocations()

        val firstItem = oneItem.first()
        assertEquals(firstItem, locationTestValue)
    }
}
