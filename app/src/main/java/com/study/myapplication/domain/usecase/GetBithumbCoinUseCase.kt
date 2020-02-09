package com.study.myapplication.domain.usecase

import com.study.myapplication.domain.repository.CoinRepository
import com.study.myapplication.domain.entity.Ticker

class GetBithumbCoinUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke(): Map<String,Ticker> {
        return repository.getBithumbCoin()
    }

}