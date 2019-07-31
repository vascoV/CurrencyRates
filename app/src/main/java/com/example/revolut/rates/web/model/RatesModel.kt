package com.example.revolut.rates.web.model

import com.google.gson.JsonObject

data class RatesModel(
    var base: String,
    var date: String,
    var rates: JsonObject
)