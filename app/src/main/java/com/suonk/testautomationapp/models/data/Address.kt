package com.suonk.testautomationapp.models.data

data class Address(
    val city: String,
    val country: String,
    val postalCode: Int,
    val street: String,
    val streetCode: String,
    val addressId: Int = 1
)