package com.exchangemaster.app.currencyconverter.domain.usecase

import com.exchangemaster.app.currencyconverter.data.model.ConversionResponse
import com.exchangemaster.app.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(private val exchangeRateRepository: CurrencyRepository) {
    suspend operator fun invoke(to: String, from: String, amount: Double): ConversionResponse {
        return exchangeRateRepository.convertCurrency(to, from, amount)
    }
}