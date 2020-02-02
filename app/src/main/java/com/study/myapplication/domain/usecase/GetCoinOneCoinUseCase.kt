package com.study.myapplication.domain.usecase

import com.study.myapplication.data.source.CoinRepository
import com.study.myapplication.domain.entity.Ticker

class GetCoinOneCoinUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke(): Map<String,Ticker> {
        return repository.getCoinOneCoin()
    }

}