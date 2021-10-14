package com.suonk.testautomationapp.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.suonk.testautomationapp.models.AppDatabase
import com.suonk.testautomationapp.models.dao.DeviceDao
import com.suonk.testautomationapp.models.dao.UserDao
import com.suonk.testautomationapp.models.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Provider

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        userDaoProvider: Provider<UserDao>,
        deviceDaoProvider: Provider<DeviceDao>
    ) =
        Room.databaseBuilder(
            context, AppDatabase::class.java, "app_database"
        )
            .allowMainThreadQueries()
            .addMigrations()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        prePopulateDatabase(
                            userDaoProvider.get(),
                            deviceDaoProvider.get()
                        )
                    }
                }

//                override fun onOpen(db: SupportSQLiteDatabase) {
//                    super.onOpen(db)
//
//                    CoroutineScope(Dispatchers.IO).launch {
//                        val json = try {
//                            val inputStream: InputStream = context.assets.open("modulo_tech.json")
//                            inputStream.bufferedReader().use {
//                                it.readText()
//                            }
//                        } catch (ex: Exception) {
//                            ex.localizedMessage
//                            ""
//                        }
//                        val jsonObj = JsonParser().parse(json).asJsonObject
//
//                        val userType = object : TypeToken<User>() {}.type
//                        val user: User = Gson().fromJson(jsonObj, userType)
//
//                        val devicesType = object : TypeToken<Device>() {}.type
//                        val user: User = Gson().fromJson(jsonObj, userType)
//
//                        populateDatabase(
//                            database,
//                            company
//                        )
//
//                        prePopulateDatabase(
//                            userDaoProvider.get(),
//                            deviceDaoProvider.get()
//                        )
//                    }
//                }

                private suspend fun prePopulateDatabase(
                    userDao: UserDao,
                    deviceDao: DeviceDao
                ) {
                    //region ===================================== User =====================================

                    userDao.addNewUser(
                        User(
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
                    )

                    //endregion

                    //region ==================================== Devices ===================================

                    deviceDao.apply {
                        val listOfDevices = listOf(
                            Device(
                                "Lampe - Cuisine",
                                "Light",
                                null,
                                Light("ON", 50),
                                null
                            ),
                            Device(
                                "Volet roulant - Salon",
                                "RollerShutter",
                                null,
                                null,
                                RollerShutter(70)
                            ),
                            Device(
                                "Radiateur - Chambre",
                                "Heater",
                                Heater("OFF", 20),
                                null,
                                null
                            ),
                            Device(
                                "Lampe - Salon",
                                "Light",
                                null,
                                Light("ON", 100),
                                null
                            ),
                            Device(
                                "Volet roulant",
                                "RollerShutter",
                                null,
                                null,
                                RollerShutter(0)
                            ),
                            Device(
                                "Radiateur - Salon",
                                "Heater",
                                Heater("OFF", 18),
                                null,
                                null
                            ),
                            Device(
                                "Lampe - Grenier",
                                "Light",
                                null,
                                Light("ON", 0),
                                null
                            ),
                            Device(
                                "Volet roulant - Salle de bain",
                                "RollerShutter",
                                null,
                                null,
                                RollerShutter(70)
                            ),
                            Device(
                                "Radiateur - Salle de bain",
                                "Heater",
                                Heater("OFF", 20),
                                null,
                                null
                            ),
                            Device(
                                "Lampe - Salle de bain",
                                "Light",
                                null,
                                Light("ON", 36),
                                null
                            ),
                            Device(
                                "Volet roulant",
                                "RollerShutter",
                                null,
                                null,
                                RollerShutter(33)
                            ),
                            Device(
                                "Radiateur - WC",
                                "Heater",
                                Heater("ON", 19),
                                null,
                                null
                            )
                        )
                        addAllDevices(listOfDevices)
                    }

                    //endregion
                }
            })
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideUserDao(database: AppDatabase) = database.userDao()

    @Provides
    fun provideDeviceDao(database: AppDatabase) = database.deviceDao()
}