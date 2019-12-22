package com.study.myapplication.api

import com.study.myapplication.api.model.MovieResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NaverApi {
    @GET("v1/search/movie.json")
    fun getMovieList(@Query("query") query: String): Single<MovieResponse>
}