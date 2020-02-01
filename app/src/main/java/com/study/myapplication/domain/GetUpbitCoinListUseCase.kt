package com.study.myapplication.domain

import com.study.myapplication.api.model.UpbitTickerResponse
import com.study.myapplication.source.CoinRepository

class GetUpbitCoinListUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke(market: String): List<UpbitTickerResponse> {
        return repository.getUpbitCoin(market)
    }

}