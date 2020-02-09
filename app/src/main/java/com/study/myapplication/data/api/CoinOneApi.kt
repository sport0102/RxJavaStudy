package com.study.myapplication.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface CoinOneApi {
    @GET("ticker")
    suspend fun getTickerInfo(@Query("currency") currency: String = "all"): Map<String, Any>
}