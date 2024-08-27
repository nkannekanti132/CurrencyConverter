package com.exchangemaster.app.currencyconverter.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exchangemaster.app.currencyconverter.data.model.ConversionResponse
import com.exchangemaster.app.currencyconverter.domain.usecase.ConvertCurrencyUseCase
import com.exchangemaster.app.currencyconverter.domain.usecase.GetExchangeRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val getExchangeRateUseCase: GetExchangeRateUseCase,

    ) : ViewModel() {
    private val _symbols = MutableLiveData<Map<String, String>>()
    val symbols: LiveData<Map<String, String>> get() = _symbols
    var str:String = ""





    private val _exchangeRateData = MutableLiveData<ConversionResponse>()
    val exchangeRateData: LiveData<ConversionResponse> get() = _exchangeRateData
    val countryValue1 = MutableLiveData<String>()
    val countryValue2 = MutableLiveData<String>()
    val country1 = MutableLiveData<String>()
    val country2 = MutableLiveData<String>()
    val exchange1 = MutableLiveData<String>()
    val exchange2 = MutableLiveData<String>()


    fun convertExchangeRate(to: String, from: String, amount: Double,convert:String) {
        viewModelScope.launch {
            try {
                val response  = convertCurrencyUseCase(to,from,amount)
                 _exchangeRateData.value = response
                if(convert == "country1")
                {
                    countryValue1.value = response.result.toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString()

                }
                if(convert == "country2")
                {
                    countryValue2.value = response.result.toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString()

                }
                Log.d("debug2",countryValue2.toString())
                Log.d("debug2",countryValue1.toString())
            } catch (e: Exception) {
                Log.d("debug1",e.toString())
            }
        }

    }



    fun getExchangeRate(to: String, from: String,convert: String) {
        viewModelScope.launch {
            try {
                val response = getExchangeRateUseCase(to,from)
                val rate = response.result.toString()
                if(convert == "country1")
                {
                    exchange1.value = "1"
                    exchange2.value = rate.toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString()
                }
                else{
                    exchange2.value = "1"
                    exchange1.value = rate.toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString()
                }
            } catch (e: Exception) {

            }
        }
    }
}