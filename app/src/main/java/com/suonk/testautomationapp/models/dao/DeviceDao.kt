package com.suonk.testautomationapp.models.dao

import androidx.room.*
import com.suonk.testautomationapp.models.data.Device
import com.suonk.testautomationapp.models.data.Heater
import com.suonk.testautomationapp.models.data.Light
import com.suonk.testautomationapp.models.data.RollerShutter
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {

    /**
     * getAllDevices() = device1, device2, device3
     */
    @Query("SELECT * FROM device_db ORDER BY deviceName ASC")
    fun getAllDevices(): Flow<List<Device>>

    /**
     * getDeviceById(id) = device1
     */
    @Query("SELECT * FROM device_db WHERE id = :deviceId")
    fun getDeviceById(deviceId: Int): Flow<Device>

    /**
     * updateDevice(device)
     */
    @Update
    suspend fun updateDevice(device: Device)

    /**
     * deleteDevice(device)
     */
    @Delete
    suspend fun deleteDevice(device: Device)

    /**
     * addNewDevice(device)
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewDevice(device: Device)

    /**
     * addAllDevices(devices)
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllDevices(devices: List<Device>)
}