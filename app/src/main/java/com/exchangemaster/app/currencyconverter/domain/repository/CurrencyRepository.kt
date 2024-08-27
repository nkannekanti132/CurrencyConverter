package com.exchangemaster.app.currencyconverter.domain.repository

import com.exchangemaster.app.currencyconverter.data.model.ConversionResponse
import com.exchangemaster.app.currencyconverter.data.model.SymbolsResponse


interface CurrencyRepository {

    suspend fun getCurrencies(): SymbolsResponse

    suspend fun convertCurrency(to: String, from: String, amount: Double): ConversionResponse

}
