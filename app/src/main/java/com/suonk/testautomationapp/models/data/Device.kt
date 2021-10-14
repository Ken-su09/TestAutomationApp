package com.suonk.testautomationapp.models.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "device_db")
data class Device(
    @ColumnInfo(name = "deviceName")
    val deviceName: String,
    @ColumnInfo(name = "productType")
    val productType: String,
    @ColumnInfo(name = "heater")
    val heater: Heater? = null,
    @ColumnInfo(name = "light")
    val light: Light? = null,
    @ColumnInfo(name = "rollerShutter")
    val rollerShutter: RollerShutter? = null,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) {
}