package com.aiden.aiden.architecturepatternstudy.api

import com.aiden.aiden.architecturepatternstudy.api.model.UpbitTickerResponse
import com.study.myapplication.api.model.UpbitMarketResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UpbitApi {

    @GET("v1/market/all/")
    fun getMarketList(): Single<List<UpbitMarketResponse>>

    @GET("v1/ticker/")
    fun getTickerInfo(@Query("markets") markets: String): Single<List<UpbitTickerResponse>>
}