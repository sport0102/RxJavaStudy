package com.study.myapplication.data.api.model


import com.google.gson.annotations.SerializedName

data class CoinOneTicker(
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("first")
    val first: String?,
    @SerializedName("high")
    val high: String?,
    @SerializedName("last")
    val last: String?,
    @SerializedName("low")
    val low: String?,
    @SerializedName("volume")
    val volume: String?,
    @SerializedName("yesterday_first")
    val yesterdayFirst: String?,
    @SerializedName("yesterday_high")
    val yesterdayHigh: String?,
    @SerializedName("yesterday_last")
    val yesterdayLast: String?,
    @SerializedName("yesterday_low")
    val yesterdayLow: String?,
    @SerializedName("yesterday_volume")
    val yesterdayVolume: String?
)