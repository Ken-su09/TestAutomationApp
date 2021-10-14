package com.suonk.testautomationapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.room.TypeConverter
import com.suonk.testautomationapp.models.data.Address
import com.suonk.testautomationapp.models.data.Heater
import com.suonk.testautomationapp.models.data.Light
import com.suonk.testautomationapp.models.data.RollerShutter
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

    @TypeConverter
    fun fromHeater(heater: Heater?): String {
        return if (heater == null) {
            " a ; 0"
        } else {
            "${heater.mode} ; ${heater.temperature}"
        }
    }

    @TypeConverter
    fun toHeater(heater: String): Heater {
        val delimiter = " ; "
        val parts = heater.split(delimiter)

        return Heater(
            parts[0],
            parts[1].toInt()
        )
    }

    @TypeConverter
    fun fromLight(light: Light?): String {
        return if (light == null) {
            " a ; 0"
        } else {
            "${light.mode} ; ${light.intensity}"
        }
    }

    @TypeConverter
    fun toLight(light: String): Light {
        val delimiter = " ; "
        val parts = light.split(delimiter)

        return Light(
            parts[0],
            parts[1].toInt()
        )
    }

    @TypeConverter
    fun fromRollerShutter(rollerShutter: RollerShutter?): String {
        return rollerShutter?.position?.toString() ?: "0"
    }

    @TypeConverter
    fun toRollerShutter(rollerShutter: String): RollerShutter {
        return RollerShutter(rollerShutter.toInt())
    }
}