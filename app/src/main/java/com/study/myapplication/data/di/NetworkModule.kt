package com.study.myapplication.data.di

import com.study.myapplication.BuildConfig
import com.study.myapplication.data.api.BithumbApi
import com.study.myapplication.data.api.CoinOneApi
import com.study.myapplication.data.api.UpbitApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.parameter.parametersOf
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

    factory<Retrofit> { (url: String) ->
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(get())
            .build()
    }

    single<UpbitApi> {
        get<Retrofit> { parametersOf(upbitUrl) }.create(UpbitApi::class.java)
    }

    single<BithumbApi> {
        get<Retrofit> { parametersOf(bithumbUrl) }.create(BithumbApi::class.java)
    }

    single<CoinOneApi> {
        get<Retrofit> { parametersOf(coinOneUrl) }.create(CoinOneApi::class.java)
    }

}