package com.exchangemaster.app.currencyconverter.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exchangemaster.app.currencyconverter.domain.usecase.GetCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(private val getCurrencyUseCase: GetCurrenciesUseCase):ViewModel() {

    private val _symbols = MutableLiveData<Map<String, String>>()
    val symbols: LiveData<Map<String, String>> get() = _symbols

    fun fetchSymbols() {
        viewModelScope.launch {
            try {
                val response = getCurrencyUseCase()
                if (response.success) {
                    _symbols.value = response.symbols
                    Log.d("debug2",_symbols.value.toString())
                }
            } catch (e: Exception) {
                // Handle error
                Log.d("debug1",e.toString())
            }
        }
    }
    public fun generateDummyData() {
        _symbols.value = mapOf(
            "AED" to "United Arab Emirates Dirham",
            "AFN" to "Afghan Afghani",
            "ALL" to "Albanian Lek",
            "AMD" to "Armenian Dram",
            "ANG" to "Netherlands Antillean Guilder",
            "AOA" to "Angolan Kwanza",
            "ARS" to "Argentine Peso",
            "AUD" to "Australian Dollar",
            "AWG" to "Aruban Florin",
            "AZN" to "Azerbaijani Manat",
            "BAM" to "Bosnia-Herzegovina Convertible Mark",
            "BBD" to "Barbadian Dollar",
            "BDT" to "Bangladeshi Taka",
            "BGN" to "Bulgarian Lev",
            "BHD" to "Bahraini Dinar",
            "BIF" to "Burundian Franc",
            "BMD" to "Bermudian Dollar",
            "BND" to "Brunei Dollar",
            "BOB" to "Bolivian Boliviano",
            "BRL" to "Brazilian Real",
            "BSD" to "Bahamian Dollar",
            "BTN" to "Bhutanese Ngultrum",
            "BWP" to "Botswana Pula",
            "BYN" to "Belarusian Ruble",
            "BZD" to "Belize Dollar"
        )
        Log.d("debug2", _symbols.value.toString())
    }
}