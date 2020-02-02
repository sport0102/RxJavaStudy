package com.study.myapplication.domain.entity

import com.study.myapplication.domain.enum.CoinExchangeStation

data class Ticker(
    var coinPrice: Double = 0.0,
    var coinExchangeStation: CoinExchangeStation
)