package com.exchangemaster.app.currencyconverter.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exchangemaster.app.currencyconverter.data.database.entities.ConversionRateEntity
import com.exchangemaster.app.currencyconverter.data.database.entities.SymbolEntity

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSymbols(symbols: List<SymbolEntity>)

    @Query("SELECT * FROM symbols")
    suspend fun getSymbols(): List<SymbolEntity>

    @Query("SELECT timestamp FROM symbols LIMIT 1")
    suspend fun getSymbolsTimestamp(): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversionRate(rate: ConversionRateEntity)

    @Query("SELECT * FROM conversion_rates WHERE fromCurrency = :from AND toCurrency = :to")
    suspend fun getConversionRate(from: String, to: String): ConversionRateEntity?
}


