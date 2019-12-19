package com.study.myapplication.source.local

import com.study.myapplication.api.model.MovieResponse
import com.study.myapplication.source.NaverDataSource
import io.reactivex.Single

class NaverLocalDataSource(private val db: NaverDatabase?) : NaverDataSource {

    override fun getMovieList(query: String) : Single<MovieResponse>? {
        return null
    }

    override fun saveMovieList(
        movieList: List<MovieResponse.Item>,
        onSuccess: () -> Unit,
        onFail: (Throwable?) -> Unit
    ) {
        Thread(Runnable {
            try {
                db!!.movieDao().insert(movieList)
                onSuccess()
            } catch (e: Exception) {
                onFail(e)
            }
        }).start()
    }

}