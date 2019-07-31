package com.example.revolut.rates.di

class Injector {

    companion object {
        lateinit var appComponent: AppComponent

        fun initAppComponent(appModule: AppModule) {
            appComponent = DaggerAppComponent.builder()
                .appModule(appModule)
                .build()
        }
    }
}