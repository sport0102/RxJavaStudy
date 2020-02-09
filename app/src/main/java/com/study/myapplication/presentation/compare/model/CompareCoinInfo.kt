package com.study.myapplication.presentation.compare.model

data class CompareCoinInfo(
    var coinName: String = "",
    var coinonePrice: String = "",
    var upbitPrice: String = "",
    var bithumbPrice: String = "",
    var recommendMarket: String = "",
    var differRatio: String = ""
)
