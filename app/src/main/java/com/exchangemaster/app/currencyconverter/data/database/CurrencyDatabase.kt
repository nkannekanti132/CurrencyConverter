package com.exchangemaster.app.currencyconverter.data.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.exchangemaster.app.currencyconverter.data.database.dao.CurrencyDao
import com.exchangemaster.app.currencyconverter.data.database.entities.SymbolEntity
import com.exchangemaster.app.currencyconverter.data.database.entities.ConversionRateEntity

@Database(entities = [SymbolEntity::class, ConversionRateEntity::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}