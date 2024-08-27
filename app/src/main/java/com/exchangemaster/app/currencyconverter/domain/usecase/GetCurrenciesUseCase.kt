package com.exchangemaster.app.currencyconverter.domain.usecase

import com.exchangemaster.app.currencyconverter.data.model.SymbolsResponse
import com.exchangemaster.app.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(private val symbolsRepository: CurrencyRepository) {
    suspend operator fun invoke(): SymbolsResponse {
        return symbolsRepository.getCurrencies()
    }
}