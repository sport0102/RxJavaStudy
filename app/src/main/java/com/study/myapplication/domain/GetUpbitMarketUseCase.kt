package com.study.myapplication.domain

import com.study.myapplication.api.model.UpbitMarketResponse
import com.study.myapplication.source.CoinRepository
import io.reactivex.Single

class GetUpbitMarketUseCase(private val repository: CoinRepository) {
    operator fun invoke(): Single<List<UpbitMarketResponse>> {
        return repository.getUpbitMarket()
    }

}