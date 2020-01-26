package com.study.myapplication.feature.compare

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aiden.aiden.architecturepatternstudy.api.model.UpbitTickerResponse
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.study.myapplication.api.model.*
import com.study.myapplication.base.BaseViewModel
import com.study.myapplication.domain.GetBithumbCoinUseCase
import com.study.myapplication.domain.GetCoinOneCoinUseCase
import com.study.myapplication.domain.GetUpbitCoinListUseCase
import com.study.myapplication.domain.GetUpbitMarketUseCase
import com.study.myapplication.utils.StringUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max
import kotlin.math.min


class CompareCoinViewModel(
    private val getUpbitMarketUseCase: GetUpbitMarketUseCase,
    private val getUpbitCoinListUseCase: GetUpbitCoinListUseCase,
    private val getBithumbCoinUseCase: GetBithumbCoinUseCase,
    private val getCoinOneCoinUseCase: GetCoinOneCoinUseCase
) : BaseViewModel() {
    private val _coinList = MutableLiveData<List<CompareCoinInfo>>()
    val coinList: LiveData<List<CompareCoinInfo>> get() = _coinList

    init {
        getCoinList()
    }

    private fun getCoinList() {
        addDisposable(
            getUpbitMarketUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    getMarketString(it)
                }
                .flatMap {
                    getUpbitCoinListUseCase(it)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                }
                .zipWith(
                    getBithumbCoinUseCase()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                ) { upbitCoin: List<UpbitTickerResponse>, bithumbCoin: BithumbTickerResponse ->
                    val bithumbCoinMap = (bithumbCoin.data as MutableMap).apply {
                        remove("date")
                    }
                    getInitCoinCompareList(upbitCoin, bithumbCoinMap)
                }.zipWith(
                    getCoinOneCoinUseCase()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                ) { compareCoinInfoList: MutableList<CompareCoinInfo>, coinOneTickerResponse: CoinOneTickerResponse ->
                    getFinalCoinCompareList(compareCoinInfoList, coinOneTickerResponse)
                }
                .subscribe({
                    _coinList.value = it
                }, {
                    Log.d("errorerror", it.message)
                })

        )
    }

    private fun getMarketString(upbitMarketList: List<UpbitMarketResponse>): String {
        val list = ArrayList<String>()
        upbitMarketList.forEach { res ->
            if (res.market.startsWith("KRW", true)) {
                list.add(res.market)
            }
        }
        return list.joinToString()
    }

    private fun getInitCoinCompareList(
        upbitCoin: List<UpbitTickerResponse>,
        bithumbCoinMap: MutableMap<String, Any>
    ): MutableList<CompareCoinInfo> {
        val coinList = mutableListOf<CompareCoinInfo>()
        upbitCoin.forEach {
            val coinName = it.market.split("-")[1].toUpperCase(Locale.US)
            val bithumbCoinInfo =
                if (bithumbCoinMap[coinName] != null) Gson().fromJson(
                    bithumbCoinMap[coinName].toString(),
                    BithumbTicker::class.java
                ) else return@forEach
            val bithumbCoinPrice: Double = bithumbCoinInfo.openingPrice?.toDouble() ?: 0.toDouble()
            coinList.add(
                CompareCoinInfo(
                    coinName = coinName,
                    upbitPrice = it.openingPrice.toString(),
                    bithumbPrice = bithumbCoinPrice.toString()
                )
            )
        }
        return coinList
    }

    private fun getFinalCoinCompareList(
        compareCoinList: List<CompareCoinInfo>,
        coinoneTickerResponse: CoinOneTickerResponse
    ): MutableList<CompareCoinInfo> {
        val newList = mutableListOf<CompareCoinInfo>()
        val coinOneTickerStr = Gson().toJson(coinoneTickerResponse)
        val element: JsonElement = JsonParser().parse(coinOneTickerStr)
        compareCoinList.forEachIndexed { index, compareCoinInfo ->
            val coinName = compareCoinInfo.coinName?.toLowerCase(Locale.US)
            val coinOneCoinInfo = element.asJsonObject.get(coinName)?.run {
                Gson().fromJson(this, CoinOneTicker::class.java)
            } ?: return@forEachIndexed
            compareCoinList[index].run {
                coinonePrice = coinOneCoinInfo.last
                val upbitPriceAsDouble = upbitPrice?.toDouble() ?: 0.toDouble()
                val bithumbPriceAsDouble = bithumbPrice?.toDouble() ?: 0.toDouble()
                val coinonePriceAsDouble = coinOneCoinInfo.last?.toDouble() ?: 0.toDouble()
                coinonePrice = StringUtil.getKrwCommaPrice(coinonePriceAsDouble)
                upbitPrice = StringUtil.getKrwCommaPrice(upbitPriceAsDouble)
                bithumbPrice = StringUtil.getKrwCommaPrice(bithumbPriceAsDouble)
                val maxPrice = max(
                    upbitPriceAsDouble,
                    max(bithumbPriceAsDouble, coinonePriceAsDouble)
                )
                val minPrice = min(
                    upbitPriceAsDouble,
                    min(bithumbPriceAsDouble, coinonePriceAsDouble)
                )
                recommendMarket =
                    when (maxPrice) {
                        upbitPriceAsDouble -> {
                            "UPBIT"
                        }
                        bithumbPriceAsDouble -> {
                            "BITHUMB"
                        }
                        coinonePriceAsDouble -> {
                            "COINONE"
                        }
                        else -> {
                            "NOTHING"
                        }

                    }
                differRatio = StringUtil.getPercent(maxPrice, minPrice)
            }
            newList.add(compareCoinList[index])
        }
        return newList
    }

}