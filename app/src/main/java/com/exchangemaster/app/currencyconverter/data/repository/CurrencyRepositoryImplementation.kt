package com.exchangemaster.app.currencyconverter.data.repository

import com.exchangemaster.app.currencyconverter.data.api.CurrencyApi
import com.exchangemaster.app.currencyconverter.data.database.dao.CurrencyDao
import com.exchangemaster.app.currencyconverter.data.database.entities.ConversionRateEntity
import com.exchangemaster.app.currencyconverter.data.database.entities.SymbolEntity
import com.exchangemaster.app.currencyconverter.data.model.ConversionResponse
import com.exchangemaster.app.currencyconverter.data.model.Info
import com.exchangemaster.app.currencyconverter.data.model.Query
import com.exchangemaster.app.currencyconverter.data.model.SymbolsResponse
import com.exchangemaster.app.currencyconverter.domain.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyRepositoryImplementation @Inject constructor(
    private val apiService: CurrencyApi,
    private val currencyDao: CurrencyDao
) : CurrencyRepository {

    override suspend fun getCurrencies(): SymbolsResponse {
        return withContext(Dispatchers.IO) {
            val cachedTimestamp = currencyDao.getSymbolsTimestamp()
            val currentTime = System.currentTimeMillis()
            val oneDayInMillis = 24 * 60 * 60 * 1000

            if (cachedTimestamp != null && (currentTime - cachedTimestamp) < oneDayInMillis) {
                // Return cached data
                val cachedSymbols = currencyDao.getSymbols()
                SymbolsResponse(true, cachedSymbols.associate { it.symbol to it.name })
            } else {
                // Fetch new data and cache it
                val call = apiService.getCurrencies()
                //val call = MockCurrencyApi().getCurrencies()
                val response = call.execute()
                if (response.isSuccessful) {
                    val symbolsResponse = response.body()!!
                    val symbols = symbolsResponse.symbols.map { SymbolEntity(it.key, it.value, currentTime) }
                    currencyDao.insertSymbols(symbols)
                    symbolsResponse
                } else {
                    throw Exception("Error fetching data")
                }
            }
        }
    }

    override suspend fun convertCurrency(to: String, from: String, amount: Double): ConversionResponse {
        return withContext(Dispatchers.IO) {
            val cachedRate = currencyDao.getConversionRate(from, to)
            val currentTime = System.currentTimeMillis()
            val oneDayInMillis = 24 * 60 * 60 * 1000

            if (cachedRate != null && (currentTime - cachedRate.timestamp) < oneDayInMillis) {
                // Use cached conversion rate
                ConversionResponse(
                    date = "", // provide a valid date
                    historical = "false",
                    info = Info(rate = cachedRate.rate, timestamp = cachedRate.timestamp),
                    query = Query(amount, from, to),
                    result = amount * cachedRate.rate,
                    success = true
                )
            } else {
                // Fetch new conversion rate and cache it
                val call = apiService.convertCurrency(to, from, amount)
                //val call = MockCurrencyApi().convertCurrency(to,from,amount)
                val response = call.execute()
                if (response.isSuccessful) {
                    val conversionResponse = response.body()!!
                    val conversionRate = ConversionRateEntity(
                        fromCurrency = from,
                        toCurrency = to,
                        rate = conversionResponse.info.rate,
                        timestamp = currentTime
                    )
                    currencyDao.insertConversionRate(conversionRate)
                    conversionResponse
                } else {
                    throw Exception("Error fetching data")
                }
            }
        }
    }

}
