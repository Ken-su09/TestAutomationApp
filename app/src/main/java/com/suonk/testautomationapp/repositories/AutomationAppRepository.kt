package com.suonk.testautomationapp.repositories

import androidx.annotation.WorkerThread
import com.suonk.testautomationapp.models.dao.AddressDao
import com.suonk.testautomationapp.models.dao.DeviceDao
import com.suonk.testautomationapp.models.dao.UserDao
import com.suonk.testautomationapp.models.data.Address
import com.suonk.testautomationapp.models.data.Device
import com.suonk.testautomationapp.models.data.User
import javax.inject.Inject

class AutomationAppRepository @Inject constructor(
    private val userDao: UserDao,
    private val deviceDao: DeviceDao,
    private val addressDao: AddressDao
) {

    //region ============================================ Devices ===========================================

    val allDevices = deviceDao.getAllDevices()
    fun getDeviceByName(deviceName: String) = deviceDao.getDeviceByDeviceName(deviceName)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addNewDevice(device: Device) {
        deviceDao.addNewDevice(device)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateDevice(device: Device) {
        deviceDao.updateDevice(device)
    }

    @WorkerThread
    suspend fun deleteDevice(device: Device) {
        deviceDao.deleteDevice(device)
    }

    //endregion

    //region ============================================= User =============================================

    val user = userDao.getUser()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    //endregion

    //region ============================================ Address ===========================================

    val address = addressDao.getAddress()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateAddress(address: Address) {
        addressDao.updateAddress(address)
    }

    //endregion
}