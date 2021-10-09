package com.suonk.testautomationapp.models.dao

import androidx.room.*
import com.suonk.testautomationapp.models.data.User
import com.suonk.testautomationapp.models.data.UserAndAddress
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    /**
     * getUser() = user1
     */
    @Query("SELECT * FROM user_db")
    fun getUser(): Flow<User>

    /**
     * getUser() = user1
     */
    @Transaction
    @Query("SELECT * FROM user_db WHERE addressId == :addressId")
    fun getUserAndAddressWithAddressId(addressId: Int): Flow<UserAndAddress>

    /**
     * addNewUser(user)
     */
    @Insert
    suspend fun addNewUser(user: User)

    /**
     * updateUser(user)
     */
    @Update
    suspend fun updateUser(user: User)
}