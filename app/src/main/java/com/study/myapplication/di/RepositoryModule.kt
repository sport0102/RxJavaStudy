package com.study.myapplication.di

import androidx.room.Room
import com.study.myapplication.source.DefaultNaverRepository
import com.study.myapplication.source.NaverDataSource
import com.study.myapplication.source.NaverRepository
import com.study.myapplication.source.local.NaverDatabase
import com.study.myapplication.source.local.NaverLocalDataSource
import com.study.myapplication.source.remote.NaverRemoteDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun getRepositoryModule() = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            NaverDatabase::class.java, "naver-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single<NaverDataSource>(named("remote")) {
        NaverRemoteDataSource(get())
    }
    single<NaverDataSource>(named("local")) {
        NaverLocalDataSource(get())
    }

    single<NaverRepository>(named("default")) {
        DefaultNaverRepository(
            get(named("remote")),
            get(named("local"))
        )
    }
}
