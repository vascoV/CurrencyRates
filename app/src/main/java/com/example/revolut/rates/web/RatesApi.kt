package com.example.revolut.rates.web

import com.example.revolut.rates.web.model.RatesModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {

    @GET("latest")
    fun getAllRates(@Query("base") base: String) : Deferred<RatesModel>
}