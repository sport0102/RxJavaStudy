package com.study.myapplication.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.study.myapplication.api.model.MovieResponse

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAll(): List<MovieResponse.Item>

    @Query("SELECT * FROM movies WHERE title like :title||'%' COLLATE NOCASE")
    fun getByMarket(title: String): List<MovieResponse.Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieList: List<MovieResponse.Item>)
}