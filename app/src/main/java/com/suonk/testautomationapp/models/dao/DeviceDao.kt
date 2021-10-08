package com.suonk.testautomationapp.models.dao

import androidx.room.*
import com.suonk.testautomationapp.models.data.Device
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {

    /**
     * getAllDevices() = device1, device2, device3
     */
    @Query("SELECT * FROM device_db ORDER BY deviceName ASC")
    fun getAllDevices(): Flow<List<Device>>

    /**
     * getAllDevices() = device1, device2, device3
     */
    @Query("SELECT * FROM device_db WHERE deviceName == :name")
    fun getDeviceByDeviceName(name: String): Flow<Device>

    /**
     * updateDevice(device)
     */
    @Update
    fun updateDevice(device: Device)

    /**
     * deleteDevice(device)
     */
    @Delete
    fun deleteDevice(device: Device)

    /**
     * addNewDevice(device)
     */
    @Insert
    fun addNewDevice(device: Device)
}