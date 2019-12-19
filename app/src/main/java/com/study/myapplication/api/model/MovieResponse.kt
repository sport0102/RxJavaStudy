package com.study.myapplication.api.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "response")
data class MovieResponse(
    @SerializedName("lastBuildDate")
    val lastBuildDate: String,
    @SerializedName("total")
    val total: Int,
    @SerializedName("start")
    val start: Int,
    @SerializedName("display")
    val display: Int,
    @SerializedName("items")
    @Embedded
    val items: List<Item>? = null

){
    constructor() : this("", 0, 0, 0, ArrayList<Item>())
    @Entity(tableName = "movies")
    data class Item(
        @PrimaryKey
        @SerializedName("title")
        val title: String="",
        @SerializedName("link")
        val link: String="",
        @SerializedName("image")
        val image: String="",
        @SerializedName("subtitle")
        val subtitle: String="",
        @SerializedName("pubDate")
        val pubDate: String="",
        @SerializedName("director")
        val director: String="",
        @SerializedName("actor")
        val actor: String="",
        @SerializedName("userRating")
        val userRating: String=""
    )
}

