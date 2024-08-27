package com.exchangemaster.app.currencyconverter.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversion_rates")
data class ConversionRateEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val fromCurrency: String,
    val toCurrency: String,
    val rate: Double,
    val timestamp: Long
)