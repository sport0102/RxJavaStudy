package com.study.myapplication.data.di

import com.study.myapplication.data.source.CoinDataSource
import com.study.myapplication.domain.repository.CoinRepository
import com.study.myapplication.data.source.DefaultCoinRepository
import com.study.myapplication.data.source.remote.CoinRemoteDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun getRepositoryModule() = module {

    single<CoinDataSource> {
        CoinRemoteDataSource(
            get(named("upbitApi")),
            get(named("bithumbApi")),
            get(named("coinOneApi"))
        )
    }

    single<CoinRepository> {
        DefaultCoinRepository(get())
    }
}
