package com.study.myapplication.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.study.myapplication.api.model.MovieResponse

@Database(entities = [MovieResponse.Item::class], version = 1)
abstract class NaverDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}