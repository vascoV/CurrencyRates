package com.example.revolut.rates.data

import com.example.revolut.rates.data.model.CurrencyResponse
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {

    @GET("latest")
    fun getAllRates(@Query("base") base: String) : Observable<CurrencyResponse>
}