package com.suonk.testautomationapp.models.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

abstract class Device(
) {
    abstract val deviceName: String
    abstract val productType: String
}