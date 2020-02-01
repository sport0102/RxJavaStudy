package com.study.myapplication.domain

import com.study.myapplication.api.model.UpbitMarketResponse
import com.study.myapplication.source.CoinRepository

class GetUpbitMarketUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke(): List<UpbitMarketResponse> {
        return repository.getUpbitMarket()
    }

}