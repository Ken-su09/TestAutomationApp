package com.suonk.testautomationapp.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.suonk.testautomationapp.models.data.Address
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {

    /**
     * getAddress() = address1
     */
    @Query("SELECT * FROM address_db")
    fun getAddress(): Flow<Address>

    /**
     * addNewAddress(address)
     */
    @Insert
    suspend fun addNewAddress(address: Address)

    /**
     * updateAddress(address)
     */
    @Update
    suspend fun updateAddress(address: Address)
}