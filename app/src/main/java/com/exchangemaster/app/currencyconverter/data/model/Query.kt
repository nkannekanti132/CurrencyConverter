package com.exchangemaster.app.currencyconverter.data.model

data class Query(
    val amount: Double,
    val from: String,
    val to: String
)