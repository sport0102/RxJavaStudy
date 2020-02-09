package com.study.myapplication.data.api

import com.study.myapplication.data.api.model.BithumbTickerResponse
import retrofit2.http.GET

interface BithumbApi {

    @GET("public/ticker/all_krw")
    suspend fun getTickerInfo(): BithumbTickerResponse
}