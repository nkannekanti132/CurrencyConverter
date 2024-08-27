package com.exchangemaster.app.currencyconverter.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "symbols")
data class SymbolEntity(
    @PrimaryKey val symbol: String,
    val name: String,
    val timestamp: Long
)
