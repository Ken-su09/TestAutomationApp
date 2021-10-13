package com.suonk.testautomationapp.models.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_db")
data class User(
    @ColumnInfo(name = "address")
    val address: Address,
    @ColumnInfo(name = "birthDate")
    val birthDate: Long,
    @ColumnInfo(name = "firstName")
    val firstName: String,
    @ColumnInfo(name = "lastName")
    val lastName: String,
    @ColumnInfo(name = "img")
    val img: Bitmap? = null,
    @ColumnInfo(name = "phoneNumber")
    val phoneNumber: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true)
    val id: Int = 0

)