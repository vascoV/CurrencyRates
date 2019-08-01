package com.example.revolut.rates.data.repository

import com.example.revolut.rates.data.RatesApi
import com.example.revolut.rates.di.Injector
import javax.inject.Inject

class CurrenciesRepository {

    @Inject
    lateinit var ratesApi: RatesApi

    init {
        Injector.appComponent.inject(this)
    }

    suspend fun getCurrenciesList(base: String): Map<String, Double> {
        val result = ratesApi.getAllRates(base).await()
        return result.rates
    }
}