package com.exchangemaster.app.currencyconverter.di

import com.exchangemaster.app.currencyconverter.data.api.CurrencyApi
import com.exchangemaster.app.currencyconverter.data.database.dao.CurrencyDao
import com.exchangemaster.app.currencyconverter.data.repository.CurrencyRepositoryImplementation
import com.exchangemaster.app.currencyconverter.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideDataRepository(currencyApi: CurrencyApi, currencyDao: CurrencyDao): CurrencyRepository {
        return CurrencyRepositoryImplementation(currencyApi, currencyDao)
    }
}
