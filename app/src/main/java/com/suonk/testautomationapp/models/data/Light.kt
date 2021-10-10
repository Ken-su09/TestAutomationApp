package com.suonk.testautomationapp.models.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.suonk.testautomationapp.R

@Entity(tableName = "light_db")
data class Light(
    @ColumnInfo(name = "deviceName")
    override val deviceName: String,
    @ColumnInfo(name = "productType")
    override val productType: String,
    @ColumnInfo(name = "intensity")
    val intensity: Int,
    @ColumnInfo(name = "mode")
    val mode: String,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) : Device()
