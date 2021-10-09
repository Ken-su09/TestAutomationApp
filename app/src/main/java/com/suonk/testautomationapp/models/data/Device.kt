package com.suonk.testautomationapp.models.data


abstract class Device(
) {
    abstract val deviceName: String
    abstract val deviceIcon: Int
    abstract val productType: String
}