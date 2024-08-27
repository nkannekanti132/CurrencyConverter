package com.exchangemaster.app.currencyconverter.data.model

data class SymbolsResponse(
    val success: Boolean,
    val symbols: Map<String, String>
)