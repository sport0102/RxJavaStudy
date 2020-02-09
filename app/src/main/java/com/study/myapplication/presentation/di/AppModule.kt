package com.study.myapplication.presentation.di

import com.study.myapplication.presentation.compare.CompareCoinViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun getAppModule() = module {
    viewModel {
        CompareCoinViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
}