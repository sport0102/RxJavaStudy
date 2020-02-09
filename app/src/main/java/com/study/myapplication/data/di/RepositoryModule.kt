package com.study.myapplication.data.di

import com.study.myapplication.data.source.CoinDataSource
import com.study.myapplication.data.source.DefaultCoinRepository
import com.study.myapplication.data.source.remote.CoinRemoteDataSource
import com.study.myapplication.domain.repository.CoinRepository
import org.koin.dsl.module

fun getRepositoryModule() = module {

    single<CoinDataSource> {
        CoinRemoteDataSource(
            get(),
            get(),
            get()
        )
    }

    single<CoinRepository> {
        DefaultCoinRepository(get())
    }
}
