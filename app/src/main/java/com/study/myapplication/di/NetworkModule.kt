package com.study.myapplication.di

import com.study.myapplication.BuildConfig
import com.study.myapplication.api.NaverApiKeyStore
import com.study.myapplication.api.NaverApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun getNetworkModule(baseUrl: String) = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(object : Interceptor{
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original = chain.request()
                    val request = original.newBuilder()
                        .header("X-Naver-Client-Id", NaverApiKeyStore.naverApiClientId)
                        .header("X-Naver-Client-Secret", NaverApiKeyStore.naverApiClientSecret)
                        .method(original.method, original.body)
                        .build()
                    return chain.proceed(request)
                }

            })
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

    single {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .baseUrl(baseUrl)
            .build()
            .create(NaverApi::class.java)
    }

}