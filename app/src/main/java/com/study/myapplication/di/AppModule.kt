package com.study.myapplication.di

import com.study.myapplication.feature.CompareCoinViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun getAppModule() = module {
    viewModel { CompareCoinViewModel(get(), get(), get()) }
}