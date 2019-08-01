package com.example.revolut.rates.data

import com.example.revolut.rates.data.model.RatesModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {

    @GET("latest")
    fun getAllRates(@Query("base") base: String) : Deferred<RatesModel>
}