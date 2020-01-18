package com.study.myapplication.di

import com.study.myapplication.domain.GetBithumbCoinUseCase
import com.study.myapplication.domain.GetUpbitCoinListUseCase
import com.study.myapplication.domain.GetUpbitMarketUseCase
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
}
