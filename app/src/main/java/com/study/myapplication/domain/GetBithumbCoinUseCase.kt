package com.study.myapplication.domain

import com.study.myapplication.api.model.BithumbTickerResponse
import com.study.myapplication.source.CoinRepository

class GetBithumbCoinUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke(): BithumbTickerResponse {
        return repository.getBithumbCoin()
    }

}