package com.study.myapplication.domain

import com.study.myapplication.api.model.BithumbTickerResponse
import com.study.myapplication.source.CoinRepository
import io.reactivex.Single

class GetBithumbCoinUseCase(private val repository: CoinRepository) {
    operator fun invoke(): Single<BithumbTickerResponse> {
        return repository.getBithumbCoin()
    }

}