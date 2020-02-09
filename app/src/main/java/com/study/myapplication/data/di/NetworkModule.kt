package com.study.myapplication.data.di

import com.study.myapplication.BuildConfig
import com.study.myapplication.data.api.BithumbApi
import com.study.myapplication.data.api.CoinOneApi
import com.study.myapplication.data.api.UpbitApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
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

    single(named("upbitApi")) {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(get())
            .baseUrl(upbitUrl)
            .build()
            .create(UpbitApi::class.java)
    }

    single(named("bithumbApi")) {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(get())
            .baseUrl(bithumbUrl)
            .build()
            .create(BithumbApi::class.java)
    }

    single(named("coinOneApi")) {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(get())
            .baseUrl(coinOneUrl)
            .build()
            .create(CoinOneApi::class.java)
    }

}