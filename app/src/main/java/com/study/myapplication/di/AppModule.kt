package com.study.myapplication.di

import com.study.myapplication.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun getAppModule() = module {
    viewModel { MainViewModel(get(named("default"))) }
}