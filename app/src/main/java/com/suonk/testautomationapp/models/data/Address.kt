package com.suonk.testautomationapp.models.data

import androidx.room.ColumnInfo
import androidx.room.Entity

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
    val streetCode: String
)