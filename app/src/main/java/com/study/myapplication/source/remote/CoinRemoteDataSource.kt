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
package com.study.myapplication.source.remote

import com.study.myapplication.api.BithumbApi
import com.aiden.aiden.architecturepatternstudy.api.UpbitApi
import com.study.myapplication.api.model.BithumbTickerResponse
import com.aiden.aiden.architecturepatternstudy.api.model.UpbitTickerResponse
import com.study.myapplication.api.model.UpbitMarketResponse
import com.study.myapplication.source.CoinDataSource
import io.reactivex.Single

class CoinRemoteDataSource(
    private val upbitApi: UpbitApi,
    private val biThumbApiApi: BithumbApi
) : CoinDataSource {

    override fun getUpbitMarket(): Single<List<UpbitMarketResponse>> = upbitApi.getMarketList()

    override fun getUpbitCoin(markets : String): Single<List<UpbitTickerResponse>> = upbitApi.getTickerInfo(markets)

    override fun getBithumbCoin(): Single<BithumbTickerResponse> = biThumbApiApi.getTickerInfo()

}

