package com.study.myapplication.feature.compare

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aiden.aiden.architecturepatternstudy.api.model.UpbitTickerResponse
import com.google.gson.Gson
import com.study.myapplication.api.model.BithumbTicker
import com.study.myapplication.api.model.BithumbTickerResponse
import com.study.myapplication.api.model.UpbitMarketResponse
import com.study.myapplication.base.BaseViewModel
import com.study.myapplication.domain.GetBithumbCoinUseCase
import com.study.myapplication.domain.GetUpbitCoinListUseCase
import com.study.myapplication.domain.GetUpbitMarketUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList


class CompareCoinViewModel(
    private val getUpbitMarketUseCase: GetUpbitMarketUseCase,
    private val getUpbitCoinListUseCase: GetUpbitCoinListUseCase,
    private val getBithumbCoinUseCase: GetBithumbCoinUseCase
) : BaseViewModel() {
    private val _coinList = MutableLiveData<List<CompareCoinInfoVo>>()
    val coinList: LiveData<List<CompareCoinInfoVo>> get() = _coinList

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
                    getCoinCompareList(upbitCoin, bithumbCoinMap)
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

    private fun getCoinCompareList(
        upbitCoin: List<UpbitTickerResponse>,
        bithumbCoinMap: MutableMap<String, Any>
    ): MutableList<CompareCoinInfoVo> {
        val coinList = mutableListOf<CompareCoinInfoVo>()
        upbitCoin.forEach {
            val coinName = it.market.split("-")[1].toUpperCase(Locale.US)
            val bithumbCoinInfo =
                if (bithumbCoinMap[coinName] != null) Gson().fromJson(
                    bithumbCoinMap[coinName].toString(),
                    BithumbTicker::class.java
                ) else return@forEach
            val bithumbCoinPrice: Double =
                bithumbCoinInfo.openingPrice?.toDouble() ?: 0.toDouble()
            val recommendMarket =
                if (it.openingPrice > bithumbCoinPrice) "Bithumb" else "Upbit"
            coinList.add(
                createCoinInfoVo(
                    "KRW",
                    coinName,
                    it.openingPrice,
                    bithumbCoinPrice,
                    recommendMarket
                )
            )
        }
        return coinList
    }

}