package com.suonk.testautomationapp.repositories

import androidx.annotation.WorkerThread
import com.suonk.testautomationapp.models.dao.DeviceDao
import com.suonk.testautomationapp.models.dao.UserDao
import com.suonk.testautomationapp.models.data.*
import javax.inject.Inject

open class AutomationAppRepository @Inject constructor(
    private val userDao: UserDao,
    private val deviceDao: DeviceDao
) : DefaultRepository {

    override fun getAllDevices() = deviceDao.getAllDevices()
    override fun getDeviceById(id: Int) = deviceDao.getDeviceById(id)

    override fun getUser() = userDao.getUser()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun updateDevice(device: Device) = deviceDao.updateDevice(device)

    @WorkerThread
    override suspend fun deleteDevice(device: Device) = deviceDao.deleteDevice(device)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun updateUser(user: User) = userDao.updateUser(user)
}