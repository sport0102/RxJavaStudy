package com.study.myapplication

import android.app.Application
import com.study.myapplication.data.di.getNetworkModule
import com.study.myapplication.data.di.getRepositoryModule
import com.study.myapplication.domain.di.getUseCaseModule
import com.study.myapplication.presentation.di.getAppModule

class MainApplication : Application() {

    private val upbitUrl = "https://api.upbit.com"
    private val bithumbUrl = "https://api.bithumb.com"
    private val coinOneUrl = "https://api.coinone.co.kr"

    override fun onCreate() {
        super.onCreate()
        setupKoin(
            this,
            getNetworkModule(
                upbitUrl,
                bithumbUrl,
                coinOneUrl
            ),
            getAppModule(),
            getRepositoryModule(),
            getUseCaseModule()
        )
    }

}