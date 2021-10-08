package com.suonk.testautomationapp.models.dao

import androidx.room.Dao
import androidx.room.Query
import com.suonk.testautomationapp.models.data.Address
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {

    /**
     * getAddress() = address1
     */
    @Query("SELECT * FROM address_db")
    fun getAddress(): Flow<Address>
}