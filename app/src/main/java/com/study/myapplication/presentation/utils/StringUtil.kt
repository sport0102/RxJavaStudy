package com.study.myapplication.presentation.utils

import kotlin.math.abs

object StringUtil {

    fun getKrwCommaPrice(price: Double): String {
        return if (price > 1_000) {
            "${String.format("%,d", (price / 1_000).toInt())}천"
        } else if (price < 1) {
            "${String.format("%,.2f", price)}"
        } else {
            "${String.format("%,d", price.toInt())}"

        }
    }

    fun getPercent(firstCompanyPrice: Double, seconCompanyPrice: Double): String {
        var percent = abs(seconCompanyPrice - firstCompanyPrice) / firstCompanyPrice
        percent *= 100
        return "-${String.format("%.2f", percent)}%"
    }
}