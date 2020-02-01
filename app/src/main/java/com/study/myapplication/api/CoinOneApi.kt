package com.study.myapplication.api

import com.study.myapplication.api.model.CoinOneTickerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinOneApi {
    @GET("ticker")
    suspend fun getTickerInfo(@Query("currency") currency: String = "all"): CoinOneTickerResponse
}