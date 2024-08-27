package com.exchangemaster.app.currencyconverter.domain.usecase

import com.exchangemaster.app.currencyconverter.data.model.ConversionResponse
import com.exchangemaster.app.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetExchangeRateUseCase @Inject constructor(private val currencyRepository: CurrencyRepository) {
        suspend operator fun invoke(to: String, from: String): ConversionResponse {
            val amount = 1.0
            return currencyRepository.convertCurrency(to, from, amount)
        }
}

