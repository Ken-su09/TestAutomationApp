package com.suonk.testautomationapp.models.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "heater_db")
data class Heater(
    @ColumnInfo(name = "deviceName")
    override val deviceName: String,
    @ColumnInfo(name = "productType")
    override val productType: String,
    @ColumnInfo(name = "mode")
    val mode: String,
    @ColumnInfo(name = "temperature")
    val temperature: Int,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) : Device()
