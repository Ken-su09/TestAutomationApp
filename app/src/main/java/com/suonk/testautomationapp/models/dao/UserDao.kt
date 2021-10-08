package com.suonk.testautomationapp.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.suonk.testautomationapp.models.data.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    /**
     * getUser() = user1
     */
    @Query("SELECT * FROM user_db")
    fun getUser(): Flow<User>

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