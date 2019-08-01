package com.example.revolut.rates.data.model

data class RatesModel(
    var base: String,
    var date: String,
    var rates: Map<String, Double>
)