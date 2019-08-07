package com.example.revolut.rates


import android.app.Application
import com.example.revolut.rates.common.BASE_URL
import com.example.revolut.rates.data.managers.NetworkManager
import com.example.revolut.rates.di.AppModule
import com.example.revolut.rates.di.Injector
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RatesApp :Application() {

    @Inject
    lateinit var retrofit: Retrofit

    private lateinit var appModule: AppModule

    companion object {
        lateinit var instance: RatesApp
            private set
    }


    override fun onCreate() {
        super.onCreate()
        instance = this

        initRetrofit()

        appModule = AppModule(retrofit)
        Injector.initAppComponent(appModule)
    }

    private fun initRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun isNetworkConnected() = NetworkManager.isNetworkAvailable(this)
}