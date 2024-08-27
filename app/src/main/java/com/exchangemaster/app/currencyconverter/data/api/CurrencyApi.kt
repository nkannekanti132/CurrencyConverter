package com.exchangemaster.app.currencyconverter.data.api

import com.exchangemaster.app.currencyconverter.data.model.ConversionResponse
import com.exchangemaster.app.currencyconverter.data.model.SymbolsResponse
import com.exchangemaster.app.currencyconverter.utils.Constant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CurrencyApi {

    @Headers("apikey: ${Constant.API_KEY}")
    @GET("symbols")
    fun getCurrencies(): Call<SymbolsResponse>

    @Headers("apikey: ${Constant.API_KEY}")
    @GET("convert")
    fun convertCurrency(
        @Query("to") to: String,
        @Query("from") from: String,
        @Query("amount") amount: Double
    ): Call<ConversionResponse>

}
