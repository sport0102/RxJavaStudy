package com.study.myapplication.di

import com.aiden.aiden.architecturepatternstudy.api.UpbitApi
import com.study.myapplication.BuildConfig
import com.study.myapplication.api.BithumbApi
import com.study.myapplication.api.CoinOneApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun getNetworkModule(upbitUrl: String, bithumbUrl: String, coinOneUrl: String) = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .build()
    }

    single {
        GsonConverterFactory.create() as Converter.Factory
    }

    single { RxJava2CallAdapterFactory.create() as CallAdapter.Factory }

    single(named("upbitApi")) {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .baseUrl(upbitUrl)
            .build()
            .create(UpbitApi::class.java)
    }

    single(named("bithumbApi")) {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .baseUrl(bithumbUrl)
            .build()
            .create(BithumbApi::class.java)
    }

    single(named("coinOneApi")) {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .baseUrl(coinOneUrl)
            .build()
            .create(CoinOneApi::class.java)
    }

}