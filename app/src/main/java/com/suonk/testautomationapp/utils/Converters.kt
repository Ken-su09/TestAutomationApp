package com.suonk.testautomationapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.room.TypeConverter
import com.suonk.testautomationapp.models.data.Address
import java.io.ByteArrayOutputStream

class Converters {

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    @TypeConverter
    fun fromAddress(address: Address): String {
        return "${address.city} ; ${address.country} ; ${address.postalCode} ; ${address.street} ; ${address.streetCode}"
    }

    @TypeConverter
    fun toAddress(address: String): Address {
        val delimiter = " ; "
        val parts = address.split(delimiter)

        Log.i("toAddress", "$parts")
        return Address(
            parts[0],
            parts[1],
            parts[2].toInt(),
            parts[3],
            parts[4]
        )
    }
}