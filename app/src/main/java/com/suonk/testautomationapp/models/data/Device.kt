package com.suonk.testautomationapp.models.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "device_db")
data class Device(
    @ColumnInfo(name = "deviceName")
    val deviceName: String,
    @ColumnInfo(name = "intensity")
    val intensity: Int?,
    @ColumnInfo(name = "mode")
    val mode: String?,
    @ColumnInfo(name = "position")
    val position: Int?,
    @ColumnInfo(name = "productType")
    val productType: String,
    @ColumnInfo(name = "temperature")
    val temperature: Int?,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)