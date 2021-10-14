package com.suonk.testautomationapp.models.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.suonk.testautomationapp.getOrAwaitValue
import com.suonk.testautomationapp.models.AppDatabase
import com.suonk.testautomationapp.models.data.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: UserDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        dao = database.userDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun addNewUser() = runBlocking {
        val user = User(
            Address(
                "Issy-les-Moulineaux",
                "France",
                92130,
                "rue Michelet",
                "2B"
            ),
            813766371000,
            "John",
            "Doe",
            null,
            "456-509(1313)",
            "john.doe@hotmail.com",
            1
        )
        dao.addNewUser(user)

        val getUser = dao.getUser().asLiveData().getOrAwaitValue()
        Truth.assertThat(getUser).isEqualTo(user)
    }

    @Test
    fun updateUser() = runBlocking {
        val user = User(
            Address(
                "Issy-les-Moulineaux",
                "France",
                92130,
                "rue Michelet",
                "2B"
            ),
            813766371000,
            "John",
            "Doe",
            null,
            "456-509(1313)",
            "john.doe@hotmail.com",
            1
        )
        dao.addNewUser(user)

        val user2 = User(
            Address(
                "Issy-les-Moulineaux",
                "France",
                92130,
                "rue Michelet",
                "2B"
            ),
            813766371000,
            "Johnny",
            "Mayer",
            null,
            "456-509(1313)",
            "johnny.mayer@hotmail.com",
            1
        )
        dao.updateUser(user2)

        val getUser = dao.getUser().asLiveData().getOrAwaitValue()
        Truth.assertThat(getUser).isEqualTo(user2)
    }
}