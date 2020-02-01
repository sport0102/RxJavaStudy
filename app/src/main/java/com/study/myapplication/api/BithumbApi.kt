package com.study.myapplication.api

import com.study.myapplication.api.model.BithumbTickerResponse
import retrofit2.http.GET

interface BithumbApi {

    @GET("public/ticker/all_krw")
    suspend fun getTickerInfo(): BithumbTickerResponse
}