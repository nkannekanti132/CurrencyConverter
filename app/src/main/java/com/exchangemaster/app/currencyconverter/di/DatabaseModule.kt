package com.exchangemaster.app.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.exchangemaster.app.currencyconverter.data.database.CurrencyDatabase
import com.exchangemaster.app.currencyconverter.data.database.dao.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): CurrencyDatabase {
        return Room.databaseBuilder(context.applicationContext, CurrencyDatabase::class.java, "currency_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCurrencyDao(database: CurrencyDatabase): CurrencyDao {
        return database.currencyDao()
    }
}
