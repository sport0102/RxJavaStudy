package com.study.myapplication.domain

import com.study.myapplication.api.model.CoinOneTickerResponse
import com.study.myapplication.source.CoinRepository

class GetCoinOneCoinUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke(): CoinOneTickerResponse {
        return repository.getCoinOneCoin()
    }

}