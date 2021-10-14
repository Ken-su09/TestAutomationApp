package com.suonk.testautomationapp.repositories

import com.suonk.testautomationapp.models.data.Device
import com.suonk.testautomationapp.models.data.User
import kotlinx.coroutines.flow.Flow

interface DefaultRepository {

    fun getAllDevices(): Flow<List<Device>>
    fun getDeviceById(id: Int): Flow<Device?>

    suspend fun updateDevice(device: Device)
    suspend fun deleteDevice(device: Device)

    fun getUser(): Flow<User?>
    suspend fun updateUser(user: User)
}