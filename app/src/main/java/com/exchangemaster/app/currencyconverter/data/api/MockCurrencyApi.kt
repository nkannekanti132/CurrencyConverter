package com.exchangemaster.app.currencyconverter.data.api

import com.exchangemaster.app.currencyconverter.data.model.ConversionResponse
import com.exchangemaster.app.currencyconverter.data.model.Info
import com.exchangemaster.app.currencyconverter.data.model.SymbolsResponse
import retrofit2.Response

import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Query

class MockCurrencyApi : CurrencyApi {
    override fun getCurrencies(): Call<SymbolsResponse> {
        val symbols = mapOf(
            "AED" to "United Arab Emirates Dirham",
            "AFN" to "Afghan Afghani",
            "ALL" to "Albanian Lek",
            "AMD" to "Armenian Dram"
        )
        val response = SymbolsResponse(success = true, symbols = symbols)
        return MockCall(response)
    }

    override fun convertCurrency(
        @Query("to") to: String,
        @Query("from") from: String,
        @Query("amount") amount: Double
    ): Call<ConversionResponse> {
        val info = Info(rate = 148.972231, timestamp = 1519328414)
        val query = com.exchangemaster.app.currencyconverter.data.model.Query(
            amount = amount,
            from = from,
            to = to
        )
        val result = amount * 148.972231
        val response = ConversionResponse(
            date = "2018-02-22",
            historical = "false",
            info = info,
            query = query,
            result = result,
            success = true
        )
        return MockCall(response)
    }

    class MockCall<T>(private val response: T) : Call<T> {
        override fun enqueue(callback: Callback<T>) {
            callback.onResponse(this, Response.success(response))
        }

        override fun isExecuted() = false
        override fun clone(): Call<T> = MockCall(response)
        override fun isCanceled() = false
        override fun cancel() {}
        override fun execute(): Response<T> = Response.success(response)
        override fun request(): okhttp3.Request {
            return okhttp3.Request.Builder().url("http://localhost/").build()
        }
    }
}

