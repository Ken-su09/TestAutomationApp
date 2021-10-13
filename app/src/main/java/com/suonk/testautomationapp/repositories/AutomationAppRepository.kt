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

    //region ============================================ Devices ===========================================

    val allLights = deviceDao.getAllLights()
    val allHeaters = deviceDao.getAllHeaters()
    val allRollerShutters = deviceDao.getAllRollerShutters()

    suspend fun updateLight(light: Light) = deviceDao.updateLight(light)
    suspend fun updateHeater(heater: Heater) = deviceDao.updateHeater(heater)
    suspend fun updateRollerShutter(rs: RollerShutter) = deviceDao.updateRollerShutter(rs)

    suspend fun deleteLight(light: Light) = deviceDao.deleteLight(light)
    suspend fun deleteHeater(heater: Heater) = deviceDao.deleteHeater(heater)
    suspend fun deleteRollerShutter(rs: RollerShutter) = deviceDao.deleteRollerShutter(rs)

    //endregion

    //region ======================================= User and Address =======================================

    val user = userDao.getUser()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    //endregion
}