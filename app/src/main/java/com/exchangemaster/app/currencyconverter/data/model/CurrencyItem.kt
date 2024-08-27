package com.exchangemaster.app.currencyconverter.data.model

sealed class CurrencyItem {
    data class Header(val letter: Char) : CurrencyItem()
    data class Item(val symbol: String, val name: String) : CurrencyItem()
}
