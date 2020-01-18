package com.study.myapplication.domain

import com.aiden.aiden.architecturepatternstudy.api.model.UpbitTickerResponse
import com.study.myapplication.source.CoinRepository
import io.reactivex.Single

class GetUpbitCoinListUseCase(private val repository: CoinRepository) {
    operator fun invoke(market: String): Single<List<UpbitTickerResponse>> {
        return repository.getUpbitCoin(market)
    }

}