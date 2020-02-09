package com.study.myapplication.domain.di

import com.study.myapplication.domain.usecase.GetBithumbCoinUseCase
import com.study.myapplication.domain.usecase.GetCoinOneCoinUseCase
import com.study.myapplication.domain.usecase.GetUpbitCoinListUseCase
import com.study.myapplication.domain.usecase.GetUpbitMarketUseCase
import org.koin.dsl.module

fun getUseCaseModule() = module {
    single {
        GetUpbitMarketUseCase(get())
    }

    single {
        GetUpbitCoinListUseCase(get())
    }

    single {
        GetBithumbCoinUseCase(get())
    }

    single {
        GetCoinOneCoinUseCase(get())
    }
}
