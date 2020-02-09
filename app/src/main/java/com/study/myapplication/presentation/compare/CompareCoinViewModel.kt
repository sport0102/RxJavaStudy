package com.study.myapplication.presentation.compare

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.myapplication.presentation.utils.event.Event
import com.study.myapplication.presentation.base.BaseViewModel
import com.study.myapplication.domain.entity.Ticker
import com.study.myapplication.domain.usecase.GetBithumbCoinUseCase
import com.study.myapplication.domain.usecase.GetCoinOneCoinUseCase
import com.study.myapplication.domain.usecase.GetUpbitCoinListUseCase
import com.study.myapplication.domain.usecase.GetUpbitMarketUseCase
import com.study.myapplication.presentation.compare.model.CompareCoinInfo
import com.study.myapplication.presentation.utils.StringUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min


class CompareCoinViewModel(
    private val getUpbitMarketUseCase: GetUpbitMarketUseCase,
    private val getUpbitCoinListUseCase: GetUpbitCoinListUseCase,
    private val getBithumbCoinUseCase: GetBithumbCoinUseCase,
    private val getCoinOneCoinUseCase: GetCoinOneCoinUseCase
) : BaseViewModel() {

    private val upbitTickerList by lazy {
        viewModelScope.async {
            getUpbitCoinListUseCase(getUpbitMarketUseCase())
        }
    }

    private val bithumbTickerList by lazy {
        viewModelScope.async {
            getBithumbCoinUseCase()
        }
    }

    private val coinOneTickerList by lazy {
        viewModelScope.async {
            getCoinOneCoinUseCase()
        }
    }

    private val _coinList = MutableLiveData<List<CompareCoinInfo>>()
    val coinList: LiveData<List<CompareCoinInfo>> get() = _coinList

    init {
        getCoinList()
    }

    private fun getCoinList() {
        viewModelScope.launch {
            val upbitTickerList = try {
                upbitTickerList.await()
            } catch (e: Exception) {
                Log.d("CompareCoinViewModel", "${e.message}")
                _isDataLoadingError.value = Event("업비트 데이터 로딩 에러 발생" to true)
                _isDataLoading.value = false
                mapOf<String, Ticker>()
            }

            val bithumbTickerList = try {
                bithumbTickerList.await()
            } catch (e: Exception) {
                Log.d("CompareCoinViewModel", "${e.message}")
                _isDataLoadingError.value = Event("빗썸 데이터 로딩 에러 발생" to true)
                _isDataLoading.value = false
                mapOf<String, Ticker>()
            }

            val coinOneTickerList = try {
                coinOneTickerList.await()
            } catch (e: Exception) {
                Log.d("CompareCoinViewModel", "${e.message}")
                _isDataLoadingError.value = Event("코인원 데이터 로딩 에러 발생" to true)
                _isDataLoading.value = false
                mapOf<String, Ticker>()
            }

            _coinList.postValue(
                getCompareCoinList(
                    upbitTickerList,
                    bithumbTickerList,
                    coinOneTickerList
                )
            )
            _isDataLoading.value = false
        }
    }

    private fun getCompareCoinList(
        upbitTickerMap: Map<String, Ticker>,
        bithumbTickerMap: Map<String, Ticker>,
        coinOneTickerMap: Map<String, Ticker>
    ): MutableList<CompareCoinInfo> {
        val compareCoinInfoList = mutableListOf<CompareCoinInfo>()
        bithumbTickerMap.forEach {
            Log.d("bithumbTickerMap", "key : ${it.key}")
            Log.d("coinOneTickerMap", "value : ${coinOneTickerMap[it.key]}")
            Log.d("upbitTickerMap", "value : ${upbitTickerMap[it.key]}")
            if (coinOneTickerMap[it.key] == null || upbitTickerMap[it.key] == null) {
                return@forEach
            }
            val upbitPrice = upbitTickerMap[it.key]?.coinPrice ?: 0.0
            val bithumbPrice = coinOneTickerMap[it.key]?.coinPrice ?: 0.0
            val coinOnePrice = bithumbTickerMap[it.key]?.coinPrice ?: 0.0

            val maxPrice = max(
                upbitPrice,
                max(bithumbPrice, coinOnePrice)
            )
            val minPrice = min(
                upbitPrice,
                min(bithumbPrice, coinOnePrice)
            )
            val compareCoinInfo = CompareCoinInfo(
                coinName = it.key,
                coinonePrice = StringUtil.getKrwCommaPrice(coinOnePrice),
                upbitPrice = StringUtil.getKrwCommaPrice(upbitPrice),
                bithumbPrice = StringUtil.getKrwCommaPrice(bithumbPrice),
                recommendMarket = when (maxPrice) {
                    upbitPrice -> {
                        "UPBIT"
                    }
                    bithumbPrice -> {
                        "BITHUMB"
                    }
                    coinOnePrice -> {
                        "COINONE"
                    }
                    else -> {
                        "NOTHING"
                    }
                },
                differRatio = StringUtil.getPercent(maxPrice, minPrice)
            )
            compareCoinInfoList.add(compareCoinInfo)
        }
        return compareCoinInfoList
    }

}