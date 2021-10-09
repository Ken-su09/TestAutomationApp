package com.suonk.testautomationapp.models.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_db")
data class Address(
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "postalCode")
    val postalCode: Int,
    @ColumnInfo(name = "street")
    val street: String,
    @ColumnInfo(name = "streetCode")
    val streetCode: String,
    @ColumnInfo(name = "addressId") @PrimaryKey(autoGenerate = false)
    val addressId: Int = 1
)