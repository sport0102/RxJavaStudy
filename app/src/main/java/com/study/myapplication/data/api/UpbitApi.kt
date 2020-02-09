package com.study.myapplication.data.api

import com.study.myapplication.data.api.model.UpbitMarketResponse
import com.study.myapplication.data.api.model.UpbitTickerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UpbitApi {

    @GET("v1/market/all/")
    suspend fun getMarketList(): List<UpbitMarketResponse>

    @GET("v1/ticker/")
    suspend fun getTickerInfo(@Query("markets") markets: String): List<UpbitTickerResponse>
}