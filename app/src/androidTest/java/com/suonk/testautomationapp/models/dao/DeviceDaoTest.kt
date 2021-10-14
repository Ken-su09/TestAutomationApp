package com.suonk.testautomationapp.models.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.suonk.testautomationapp.getOrAwaitValue
import com.suonk.testautomationapp.models.AppDatabase
import com.suonk.testautomationapp.models.data.Device
import com.suonk.testautomationapp.models.data.Heater
import com.suonk.testautomationapp.models.data.Light
import com.suonk.testautomationapp.models.data.RollerShutter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DeviceDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: DeviceDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        dao = database.deviceDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun addNewDevice() = runBlocking {
        val device = Device(
            "deviceName",
            "Light",
            Heater(" a ", 0),
            Light("ON", 50),
            RollerShutter(0),
            1
        )
        dao.addNewDevice(device)

        val allDevices = dao.getAllDevices().asLiveData().getOrAwaitValue()
        Truth.assertThat(allDevices).contains(device)
    }

    @Test
    fun updateDevice() = runBlocking {
        val device = Device(
            "deviceName",
            "Light",
            Heater(" a ", 0),
            Light("ON", 50),
            RollerShutter(0),
            1
        )
        dao.addNewDevice(device)

        val device2 = Device(
            "deviceName2",
            "Light",
            Heater(" a ", 0),
            Light("OFF", 40),
            RollerShutter(0),
            1
        )
        dao.updateDevice(device2)

        val allDevices = dao.getAllDevices().asLiveData().getOrAwaitValue()
        Truth.assertThat(allDevices).contains(device2)
    }

    @Test
    fun deleteDevice() = runBlocking {
        val device = Device(
            "deviceName",
            "Light",
            Heater(" a ", 0),
            Light("ON", 50),
            RollerShutter(0),
            1
        )
        dao.addNewDevice(device)
        dao.deleteDevice(device)

        val allDevices = dao.getAllDevices().asLiveData().getOrAwaitValue()
        Truth.assertThat(allDevices).doesNotContain(device)
    }

    @Test
    fun addAllDevices() = runBlocking {
        val listOfDevices = listOf(
            Device(
                "deviceName",
                "Light",
                Heater(" a ", 0),
                Light("ON", 50),
                RollerShutter(0),
                1
            ),
            Device(
                "deviceName",
                "RollerShutter",
                Heater(" a ", 0),
                Light(" a ", 0),
                RollerShutter(75),
                2
            ),
            Device(
                "deviceName",
                "Heater",
                Heater("ON", 18),
                Light(" a ", 0),
                RollerShutter(0),
                3
            )
        )
        dao.addAllDevices(listOfDevices)

        val allDevices = dao.getAllDevices().asLiveData().getOrAwaitValue()
        Truth.assertThat(allDevices).isEqualTo(listOfDevices)
    }

    @Test
    fun getDeviceById() = runBlocking {
        val listOfDevices = listOf(
            Device(
                "deviceName",
                "Light",
                Heater(" a ", 0),
                Light("ON", 50),
                RollerShutter(0),
                1
            ),
            Device(
                "deviceName",
                "RollerShutter",
                Heater(" a ", 0),
                Light(" a ", 0),
                RollerShutter(75),
                2
            ),
            Device(
                "deviceName",
                "Heater",
                Heater("ON", 18),
                Light(" a ", 0),
                RollerShutter(0),
                3
            )
        )
        dao.addAllDevices(listOfDevices)

        val allDevices = dao.getAllDevices().asLiveData().getOrAwaitValue()
        val device = dao.getDeviceById(1).asLiveData().getOrAwaitValue()

        Truth.assertThat(allDevices).contains(device)
    }

    @Test
    fun getAllDevices() = runBlocking {
        val listOfDevices = listOf(
            Device(
                "deviceName",
                "Light",
                Heater(" a ", 0),
                Light("ON", 50),
                RollerShutter(0),
                1
            ),
            Device(
                "deviceName",
                "RollerShutter",
                Heater(" a ", 0),
                Light(" a ", 0),
                RollerShutter(75),
                2
            ),
            Device(
                "deviceName",
                "Heater",
                Heater("ON", 18),
                Light(" a ", 0),
                RollerShutter(0),
                3
            )
        )
        dao.addAllDevices(listOfDevices)

        val allDevices = dao.getAllDevices().asLiveData().getOrAwaitValue()
        Truth.assertThat(listOfDevices).isEqualTo(allDevices)
    }
}