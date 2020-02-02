package com.study.myapplication.domain.usecase

import com.study.myapplication.data.source.CoinRepository
import com.study.myapplication.domain.entity.Market

class GetUpbitMarketUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke(): List<Market> {
        return repository.getUpbitMarket()
    }

}