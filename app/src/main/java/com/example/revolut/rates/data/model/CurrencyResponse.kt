package com.example.revolut.rates.data.model

data class CurrencyResponse(
    var base: String,
    var date: String,
    var rates: Map<String, Double>
)