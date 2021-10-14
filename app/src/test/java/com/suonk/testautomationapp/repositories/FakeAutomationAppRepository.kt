package com.suonk.testautomationapp.repositories

import com.suonk.testautomationapp.models.data.Device
import com.suonk.testautomationapp.models.data.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAutomationAppRepository : DefaultRepository {

    private val devicesList = mutableListOf<Device>()
    private val userList = mutableListOf<User>()

    override fun getAllDevices(): Flow<List<Device>> {
        return flow { emit(devicesList) }
    }

    override fun getDeviceById(id: Int): Flow<Device?> {
        return flow { emit(devicesList.find { it.id == id }) }
    }

    override suspend fun updateDevice(device: Device) {
        devicesList[devicesList.indexOf(device)] = device
    }

    override suspend fun deleteDevice(device: Device) {
        devicesList.remove(device)
    }

    override fun getUser(): Flow<User?> {
        return flow { emit(userList[0]) }
    }

    override suspend fun updateUser(user: User) {
        userList[userList.indexOf(user)] = user
    }
}