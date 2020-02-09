package com.study.myapplication.presentation.compare.model

data class CompareCoinInfo(
    var coinName: String? = null,
    var coinonePrice: String? = null,
    var upbitPrice: String? = null,
    var bithumbPrice: String? = null,
    var recommendMarket: String? = null,
    var differRatio: String? = null
)
