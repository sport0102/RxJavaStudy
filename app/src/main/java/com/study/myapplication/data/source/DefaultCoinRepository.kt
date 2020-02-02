/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.study.myapplication.data.source

import com.google.gson.Gson
import com.study.myapplication.api.model.BithumbTicker
import com.study.myapplication.api.model.CoinOneTicker
import com.study.myapplication.domain.entity.Market
import com.study.myapplication.domain.entity.Ticker
import com.study.myapplication.domain.enum.CoinExchangeStation
import java.util.*

class DefaultCoinRepository(
    private val remoteDataSource: CoinDataSource
) : CoinRepository {

    override suspend fun getUpbitMarket(): List<Market> =
        remoteDataSource.getUpbitMarket()
            .filter { it.market.startsWith("KRW") }
            .map { res ->
                Market(res.market)
            }


    override suspend fun getUpbitCoin(marketList: List<Market>): Map<String, Ticker> =
        remoteDataSource.getUpbitCoin(marketList.map { it.marketName }.joinToString()).map {
            it.market.split("-")[1].toUpperCase(Locale.US) to Ticker(
                coinPrice = it.openingPrice, coinExchangeStation = CoinExchangeStation.UPBIT
            )
        }.toMap()


    override suspend fun getBithumbCoin(): Map<String, Ticker> =
        (remoteDataSource.getBithumbCoin().data as MutableMap)
            .filter { it.key != "date" }
            .map {
                val bithumbTicker = Gson().fromJson(
                    it.value.toString(),
                    BithumbTicker::class.java
                )
                it.key.toUpperCase(Locale.US) to Ticker(
                    coinPrice = bithumbTicker.openingPrice?.toDouble() ?: 0.0,
                    coinExchangeStation = CoinExchangeStation.BITHUMB
                )
            }.toMap()

    override suspend fun getCoinOneCoin(): Map<String, Ticker> {
        return remoteDataSource.getCoinOneCoin()
            .filter { it.key != "timestamp" && it.key != "result" && it.key != "errorCode" }
            .map {
                val coinOneCoinInfo = Gson().fromJson(
                    it.value.toString(), CoinOneTicker::class.java
                )
                it.key.toUpperCase(Locale.US) to Ticker(
                    coinPrice = coinOneCoinInfo.last?.toDouble() ?: 0.0,
                    coinExchangeStation = CoinExchangeStation.COINONE
                )
            }.toMap()
    }

}
