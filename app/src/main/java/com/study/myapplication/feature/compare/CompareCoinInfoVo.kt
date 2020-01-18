package com.study.myapplication.feature.compare

import com.study.myapplication.utils.StringUtil

data class CompareCoinInfoVo(
    var market: String,
    var coinName: String,
    var upbitPrice: String,
    var bithumbPrice: String,
    var recommendMarket: String,
    var differRatio: String
)

fun createCoinInfoVo(
    market: String,
    coinName: String,
    upbitPrice: Double,
    bithumbPrice: Double,
    recommendMarket: String
): CompareCoinInfoVo {

    return CompareCoinInfoVo(
        market,
        coinName,
        StringUtil.getKrwCommaPrice(upbitPrice),
        StringUtil.getKrwCommaPrice(bithumbPrice),
        recommendMarket,
        StringUtil.getPercent(upbitPrice, bithumbPrice)
    )
}