package com.suonk.testautomationapp.models.dao

import androidx.room.*
import com.suonk.testautomationapp.models.data.Heater
import com.suonk.testautomationapp.models.data.Light
import com.suonk.testautomationapp.models.data.RollerShutter
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {

    //region ============================================ Heater ============================================

    /**
     * getAllHeaters() = heater1, heater2, heater3
     */
    @Query("SELECT * FROM heater_db ORDER BY deviceName ASC")
    fun getAllHeaters(): Flow<List<Heater>>

    /**
     * updateHeater(heater)
     */
    @Update
    suspend fun updateHeater(heater: Heater)

    /**
     * deleteHeater(heater)
     */
    @Delete
    suspend fun deleteHeater(heater: Heater)

    /**
     * addNewHeater(heater)
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewHeater(heater: Heater)

    //endregion

    //region ============================================ Light =============================================

    /**
     * getAllLights() = light1, light2, light3
     */
    @Query("SELECT * FROM light_db ORDER BY deviceName ASC")
    fun getAllLights(): Flow<List<Light>>

    /**
     * updateLight(light)
     */
    @Update
    suspend fun updateLight(light: Light)

    /**
     * deleteLight(light)
     */
    @Delete
    suspend fun deleteLight(light: Light)

    /**
     * addNewLight(light)
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewLight(light: Light)

    /**
     * insertAllLights(lights)
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLights(lights: List<Light>)

    //endregion

    //region ======================================== Roller Shutter ========================================

    /**
     * getAllRollerShutters() = rollerShutter1, rollerShutter2, rollerShutter3
     */
    @Query("SELECT * FROM roller_shutter_db ORDER BY deviceName ASC")
    fun getAllRollerShutters(): Flow<List<RollerShutter>>

    /**
     * updateRollerShutter(rollerShutter)
     */
    @Update
    suspend fun updateRollerShutter(rollerShutter: RollerShutter)

    /**
     * deleteRollerShutter(rollerShutter)
     */
    @Delete
    suspend fun deleteRollerShutter(rollerShutter: RollerShutter)

    /**
     * addNewRollerShutter(rollerShutter)
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewRollerShutter(rollerShutter: RollerShutter)

    //endregion
}