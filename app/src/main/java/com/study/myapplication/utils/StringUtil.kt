package com.study.myapplication.utils

import kotlin.math.abs

object StringUtil {

    fun getKrwCommaPrice(price: Double): String {
        return if (price > 1_000) {
            "${String.format("%,d", (price / 1_000).toInt())}천원"
        } else if (price < 1) {
            "${String.format("%,.2f", price)}원"
        } else {
            "${String.format("%,d", price.toInt())}원"

        }
    }

    fun getPercent(firstCompanyPrice: Double, seconCompanyPrice: Double): String {
        var percent = abs(seconCompanyPrice - firstCompanyPrice) / firstCompanyPrice
        percent *= 100
        return "-${String.format("%.2f", percent)}%"
    }
}