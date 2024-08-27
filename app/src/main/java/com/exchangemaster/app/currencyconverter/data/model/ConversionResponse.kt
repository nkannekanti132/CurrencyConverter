package com.exchangemaster.app.currencyconverter.data.model

data class ConversionResponse(
    val date: String,
    val historical: String,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
)