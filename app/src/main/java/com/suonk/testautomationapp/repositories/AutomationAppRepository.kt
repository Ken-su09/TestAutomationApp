package com.suonk.testautomationapp.repositories

import androidx.annotation.WorkerThread
import com.suonk.testautomationapp.models.dao.AddressDao
import com.suonk.testautomationapp.models.dao.DeviceDao
import com.suonk.testautomationapp.models.dao.UserDao
import com.suonk.testautomationapp.models.data.*
import javax.inject.Inject

class AutomationAppRepository @Inject constructor(
    private val userDao: UserDao,
    private val deviceDao: DeviceDao,
    private val addressDao: AddressDao
) {

    //region ============================================ Devices ===========================================

    val allLights = deviceDao.getAllLights()
    val allHeaters = deviceDao.getAllHeaters()
    val allRollerShutters = deviceDao.getAllRollerShutters()

    fun getLightByName(name: String) = deviceDao.getLightByDeviceName(name)
    fun getHeaterByName(name: String) = deviceDao.getHeaterByDeviceName(name)
    fun getRollerShutterByName(name: String) = deviceDao.getRollerShutterByDeviceName(name)

    suspend fun addNewLight(light: Light) = deviceDao.addNewLight(light)
    suspend fun addNewHeater(heater: Heater) = deviceDao.addNewHeater(heater)
    suspend fun addNewRollerShutter(rs: RollerShutter) = deviceDao.addNewRollerShutter(rs)

    suspend fun updateLight(light: Light) = deviceDao.updateLight(light)
    suspend fun updateHeater(heater: Heater) = deviceDao.updateHeater(heater)
    suspend fun updateRollerShutter(rs: RollerShutter) = deviceDao.updateRollerShutter(rs)

    suspend fun deleteLight(light: Light) = deviceDao.deleteLight(light)
    suspend fun deleteHeater(heater: Heater) = deviceDao.deleteHeater(heater)
    suspend fun deleteRollerShutter(rs: RollerShutter) = deviceDao.deleteRollerShutter(rs)

    //endregion

    //region ======================================= User and Address =======================================

    val user = userDao.getUser()
    val address = addressDao.getAddress()
    fun userAndAddressWithAddressId(id: Int) = userDao.getUserAndAddressWithAddressId(id)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateAddress(address: Address) {
        addressDao.updateAddress(address)
    }

    //endregion
}