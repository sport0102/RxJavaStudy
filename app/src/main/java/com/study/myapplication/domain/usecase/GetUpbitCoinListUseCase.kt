package com.study.myapplication.domain.usecase

import com.study.myapplication.domain.repository.CoinRepository
import com.study.myapplication.domain.entity.Market
import com.study.myapplication.domain.entity.Ticker

class GetUpbitCoinListUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke(markeList: List<Market>): Map<String,Ticker> {
        return repository.getUpbitCoin(markeList)
    }

}