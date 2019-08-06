package com.example.revolut.rates.data.repository

import com.example.revolut.rates.data.RatesApi
import com.example.revolut.rates.data.model.CurrencyResponse
import com.example.revolut.rates.di.Injector
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrenciesRepository {

    @Inject
    lateinit var ratesApi: RatesApi

    init {
        Injector.appComponent.inject(this)
    }

    companion object {
        const val REPEAT_RATE = 1L
    }

    fun getCurrenciesList(base: String): Observable<CurrencyResponse> {

        return ratesApi.getAllRates(base).delay(REPEAT_RATE, TimeUnit.SECONDS).repeat()
    }
}