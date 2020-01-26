package com.study.myapplication.api

import com.study.myapplication.api.model.CoinOneTickerResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinOneApi {
    @GET("ticker")
    fun getTickerInfo(@Query("currency") currency: String = "all"): Single<CoinOneTickerResponse>
}