package com.suonk.testautomationapp.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.suonk.testautomationapp.models.AppDatabase
import com.suonk.testautomationapp.models.dao.AddressDao
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
import javax.inject.Provider

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        userDaoProvider: Provider<UserDao>,
        deviceDaoProvider: Provider<DeviceDao>,
        addressDaoProvider: Provider<AddressDao>
    ) =
        Room.databaseBuilder(
            context, AppDatabase::class.java, "app_database"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        prePopulateDatabase(
                            userDaoProvider.get(),
                            deviceDaoProvider.get(),
                            addressDaoProvider.get()
                        )
                    }
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                }

                private suspend fun prePopulateDatabase(
                    userDao: UserDao,
                    deviceDao: DeviceDao,
                    addressDao: AddressDao
                ) {
                    //region ===================================== User =====================================

                    addressDao.addNewAddress(
                        Address(
                            "Issy-les-Moulineaux",
                            "France",
                            92130,
                            "rue Michelet",
                            "2B"
                        )
                    )
                    userDao.addNewUser(
                        User(
                            1,
                            813766371000,
                            "John",
                            "Doe",
                            null,
                            "456-509(1313)",
                            "john.doe@hotmail.com",
                            1
                        )
                    )

                    Log.i(
                        "prePopulateDatabase", "${
                            User(
                                1,
                                813766371000,
                                "John",
                                "Doe",
                                null,
                                "456-509(1313)",
                                "john.doe@hotmail.com",
                                1
                            )
                        }"
                    )

                    //endregion

                    //region ==================================== Devices ===================================

                    deviceDao.apply {

                        val listOfLights = listOf(
                            Light(
                                "Lampe - Cuisine",
                                "Light",
                                50,
                                "ON",
                                1
                            ),
                            Light(
                                "Lampe - Salon",
                                "Light",
                                100,
                                "ON",
                                4
                            ),
                            Light(
                                "Lampe - Salle de bain",
                                "Light",
                                36,
                                "ON",
                                10
                            ),
                            Light(
                                "Lampe - Grenier",
                                "Light",
                                0,
                                "ON",
                                7
                            )
                        )

                        insertAllLights(listOfLights)

                        // Roller Shutter
                        addNewRollerShutter(
                            RollerShutter(
                                "Volet roulant - Salon",
                                "RollerShutter",
                                70
                            )
                        )
                        addNewRollerShutter(
                            RollerShutter(
                                "Volet roulant",
                                "RollerShutter",
                                0
                            )
                        )
                        addNewRollerShutter(
                            RollerShutter(
                                "Volet roulant - Salle de bain",
                                "RollerShutter",
                                70
                            )
                        )
                        addNewRollerShutter(
                            RollerShutter(
                                "Volet roulant",
                                "RollerShutter",
                                33
                            )
                        )

                        // Heater
                        addNewHeater(
                            Heater(
                                "Radiateur - Chambre",
                                "Heater",
                                "OFF",
                                20
                            )
                        )
                        addNewHeater(
                            Heater(
                                "Radiateur - Salon",
                                "Heater",
                                "OFF",
                                18
                            )
                        )
                        addNewHeater(
                            Heater(
                                "Radiateur - Salle de bain",
                                "Heater",
                                "OFF",
                                20
                            )
                        )
                        addNewHeater(
                            Heater(
                                "Radiateur - WC",
                                "Heater",
                                "ON",
                                19
                            )
                        )


                        Log.i(
                            "prePopulateDatabase", "${
                                Heater(
                                    "Radiateur - WC",
                                    "Heater",
                                    "ON",
                                    19
                                )
                            }"
                        )
                    }

                    //endregion
                }
            })
            .allowMainThreadQueries()
            .addMigrations()
            .build()

    @Provides
    fun provideUserDao(database: AppDatabase) = database.userDao()

    @Provides
    fun provideDeviceDao(database: AppDatabase) = database.deviceDao()

    @Provides
    fun provideAddressDao(database: AppDatabase) = database.addressDao()
}