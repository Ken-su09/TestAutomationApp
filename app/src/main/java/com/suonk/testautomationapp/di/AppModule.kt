package com.suonk.testautomationapp.di

import android.content.Context
import androidx.room.Room
import com.suonk.testautomationapp.models.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, "app_database"
    )
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