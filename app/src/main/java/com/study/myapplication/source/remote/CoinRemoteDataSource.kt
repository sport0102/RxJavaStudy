package com.study.myapplication.source.remote

import com.study.myapplication.api.BithumbApi
import com.study.myapplication.api.CoinOneApi
import com.study.myapplication.api.UpbitApi
import com.study.myapplication.api.model.BithumbTickerResponse
import com.study.myapplication.api.model.CoinOneTickerResponse
import com.study.myapplication.api.model.UpbitMarketResponse
import com.study.myapplication.api.model.UpbitTickerResponse
import com.study.myapplication.source.CoinDataSource

class CoinRemoteDataSource(
    private val upbitApi: UpbitApi,
    private val biThumbApiApi: BithumbApi,
    private val coinOneApi: CoinOneApi
) : CoinDataSource {

    override suspend fun getUpbitMarket(): List<UpbitMarketResponse> = upbitApi.getMarketList()

    override suspend fun getUpbitCoin(markets: String): List<UpbitTickerResponse> =
        upbitApi.getTickerInfo(markets)

    override suspend fun getBithumbCoin(): BithumbTickerResponse = biThumbApiApi.getTickerInfo()

    override suspend fun getCoinOneCoin(): CoinOneTickerResponse = coinOneApi.getTickerInfo()

}

