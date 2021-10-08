package com.suonk.testautomationapp.models

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.suonk.testautomationapp.models.dao.AddressDao
import com.suonk.testautomationapp.models.dao.DeviceDao
import com.suonk.testautomationapp.models.dao.UserDao
import com.suonk.testautomationapp.models.data.*
import com.suonk.testautomationapp.utils.Converters

@Database(
    entities = [User::class, Address::class, Light::class, RollerShutter::class, Heater::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun deviceDao(): DeviceDao
    abstract fun addressDao(): AddressDao
}