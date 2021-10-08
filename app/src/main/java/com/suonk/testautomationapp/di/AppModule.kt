package com.suonk.testautomationapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.suonk.testautomationapp.models.AppDatabase
import com.suonk.testautomationapp.models.dao.AddressDao
import com.suonk.testautomationapp.models.dao.DeviceDao
import com.suonk.testautomationapp.models.dao.UserDao
import com.suonk.testautomationapp.models.data.Address
import com.suonk.testautomationapp.models.data.Device
import com.suonk.testautomationapp.models.data.User
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
        userDao: Provider<UserDao>,
        deviceDao: Provider<DeviceDao>,
        addressDao: Provider<AddressDao>
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
                            userDao.get(),
                            deviceDao.get(),
                            addressDao.get()
                        )
                    }
                }

                private suspend fun prePopulateDatabase(
                    userDao: UserDao,
                    deviceDao: DeviceDao,
                    addressDao: AddressDao
                ) {
                    //region ========================================= User =========================================

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
                            "john.doe@hotmail.com"
                        )
                    )

                    //endregion

                    //region ======================================== Devices =======================================

                    deviceDao.apply {
                        // 1
                        addNewDevice(
                            Device(
                                "Lampe - Cuisine",
                                50,
                                "ON",
                                null,
                                "Light",
                                null
                            )
                        )

                        // 2
                        addNewDevice(
                            Device(
                                "Volet roulant - Salon",
                                null,
                                null,
                                70,
                                "RollerShutter",
                                null
                            )
                        )

                        // 3
                        addNewDevice(
                            Device(
                                "Radiateur - Chambre",
                                null,
                                "OFF",
                                null,
                                "Heater",
                                20
                            )
                        )

                        // 4
                        addNewDevice(
                            Device(
                                "Lampe - Salon",
                                100,
                                "ON",
                                null,
                                "Light",
                                null
                            )
                        )

                        // 5
                        addNewDevice(
                            Device(
                                "Volet roulant",
                                null,
                                null,
                                0,
                                "RollerShutter",
                                null
                            )
                        )

                        // 6
                        addNewDevice(
                            Device(
                                "Radiateur - Salon",
                                null,
                                "OFF",
                                null,
                                "Heater",
                                18
                            )
                        )

                        // 7
                        addNewDevice(
                            Device(
                                "Lampe - Grenier",
                                0,
                                "ON",
                                null,
                                "Light",
                                null
                            )
                        )

                        // 8
                        addNewDevice(
                            Device(
                                "Volet roulant - Salle de bain",
                                null,
                                null,
                                70,
                                "RollerShutter",
                                null
                            )
                        )

                        // 9
                        addNewDevice(
                            Device(
                                "Radiateur - Salle de bain",
                                null,
                                "OFF",
                                null,
                                "Heater",
                                20
                            )
                        )

                        // 10
                        addNewDevice(
                            Device(
                                "Lampe - Salle de bain",
                                36,
                                "ON",
                                null,
                                "Light",
                                null
                            )
                        )

                        // 11
                        addNewDevice(
                            Device(
                                "Volet roulant",
                                null,
                                null,
                                33,
                                "RollerShutter",
                                null
                            )
                        )

                        // 12
                        addNewDevice(
                            Device(
                                "Radiateur - WC",
                                null,
                                "ON",
                                null,
                                "Heater",
                                19
                            )
                        )
                    }

                    //endregion
                }
            })
            .build()

    @Provides
    fun provideUserDao(database: AppDatabase) = database.userDao()

    @Provides
    fun provideDeviceDao(database: AppDatabase) = database.deviceDao()

    @Provides
    fun provideAddressDao(database: AppDatabase) = database.addressDao()
}