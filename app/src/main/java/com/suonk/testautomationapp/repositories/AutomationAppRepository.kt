package com.suonk.testautomationapp.repositories

import androidx.annotation.WorkerThread
import com.suonk.testautomationapp.models.dao.DeviceDao
import com.suonk.testautomationapp.models.dao.UserDao
import com.suonk.testautomationapp.models.data.*
import javax.inject.Inject

class AutomationAppRepository @Inject constructor(
    private val userDao: UserDao,
    private val deviceDao: DeviceDao
) {

    val allDevices = deviceDao.getAllDevices()
    val user = userDao.getUser()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateDevice(device: Device) = deviceDao.updateDevice(device)

    @WorkerThread
    suspend fun deleteDevice(device: Device) = deviceDao.deleteDevice(device)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
}