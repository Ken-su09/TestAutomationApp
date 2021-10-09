package com.suonk.testautomationapp.models.data

import androidx.room.Embedded
import androidx.room.Relation

data class UserAndAddress(
    @Embedded val user: User,
    @Relation(
        parentColumn = "addressId",
        entityColumn = "addressId"
    )
    val address: Address
)
