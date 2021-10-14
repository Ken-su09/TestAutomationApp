package com.suonk.testautomationapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
            .fallbackToDestructiveMigration()
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

                private suspend fun prePopulateDatabase(userDao: UserDao, deviceDao: DeviceDao) {

                    //region ==================================== Devices ===================================

                    val listOfDevices = listOf(
                        Device(
                            "Lampe - Cuisine",
                            "Light",
                            null,
                            Light("ON", 50),
                            null,
                            1
                        ),
                        Device(
                            "Volet roulant - Salon",
                            "RollerShutter",
                            null,
                            null,
                            RollerShutter(70),
                            2
                        ),
                        Device(
                            "Radiateur - Chambre",
                            "Heater",
                            Heater("OFF", 20),
                            null,
                            null,
                            3
                        ),
                        Device(
                            "Lampe - Salon",
                            "Light",
                            null,
                            Light("ON", 100),
                            null,
                            4
                        ),
                        Device(
                            "Volet roulant",
                            "RollerShutter",
                            null,
                            null,
                            RollerShutter(0),
                            5
                        ),
                        Device(
                            "Radiateur - Salon",
                            "Heater",
                            Heater("OFF", 18),
                            null,
                            null,
                            6
                        ),
                        Device(
                            "Lampe - Grenier",
                            "Light",
                            null,
                            Light("ON", 0),
                            null,
                            7
                        ),
                        Device(
                            "Volet roulant - Salle de bain",
                            "RollerShutter",
                            null,
                            null,
                            RollerShutter(70),
                            8
                        ),
                        Device(
                            "Radiateur - Salle de bain",
                            "Heater",
                            Heater("OFF", 20),
                            null,
                            null,
                            9
                        ),
                        Device(
                            "Lampe - Salle de bain",
                            "Light",
                            null,
                            Light("ON", 36),
                            null,
                            10
                        ),
                        Device(
                            "Volet roulant",
                            "RollerShutter",
                            null,
                            null,
                            RollerShutter(33),
                            11
                        ),
                        Device(
                            "Radiateur - WC",
                            "Heater",
                            Heater("ON", 19),
                            null,
                            null,
                            12
                        )
                    )
                    deviceDao.addAllDevices(listOfDevices)

                    //endregion

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
                }
            })
            .build()

    @Provides
    fun provideUserDao(database: AppDatabase) = database.userDao()

    @Provides
    fun provideDeviceDao(database: AppDatabase) = database.deviceDao()
}