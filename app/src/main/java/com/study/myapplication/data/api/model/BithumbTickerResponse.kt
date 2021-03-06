package com.study.myapplication.data.api.model


import com.google.gson.annotations.SerializedName

data class BithumbTickerResponse(
    @SerializedName("data")
    val data: Map<String, Any>?,
    @SerializedName("status")
    val status: String?
)
