package com.suonk.testautomationapp.models.dao

import androidx.room.*
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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewAddress(address: Address)

    /**
     * updateAddress(address)
     */
    @Update
    suspend fun updateAddress(address: Address)
}