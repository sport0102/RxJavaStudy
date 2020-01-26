package com.study.myapplication

import android.app.Application
import com.study.myapplication.di.getAppModule
import com.study.myapplication.di.getNetworkModule
import com.study.myapplication.di.getRepositoryModule
import com.study.myapplication.di.getUseCaseModule
import com.study.myapplication.ext.setupKoin

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