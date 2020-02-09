package com.study.myapplication

import android.app.Application
import com.study.myapplication.presentation.di.getAppModule
import com.study.myapplication.data.di.getNetworkModule
import com.study.myapplication.data.di.getRepositoryModule
import com.study.myapplication.domain.di.getUseCaseModule

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin(
            this,
            getNetworkModule(
                "https://api.upbit.com",
                "https://api.bithumb.com",
                "https://api.coinone.co.kr"
            ),
            getAppModule(),
            getRepositoryModule(),
            getUseCaseModule()
        )
    }

}