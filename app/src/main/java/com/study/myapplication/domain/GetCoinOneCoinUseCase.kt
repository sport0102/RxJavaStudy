package com.study.myapplication.domain

import com.study.myapplication.api.model.CoinOneTickerResponse
import com.study.myapplication.source.CoinRepository
import io.reactivex.Single

class GetCoinOneCoinUseCase(private val repository: CoinRepository) {
    operator fun invoke(): Single<CoinOneTickerResponse> {
        return repository.getCoinOneCoin()
    }

}