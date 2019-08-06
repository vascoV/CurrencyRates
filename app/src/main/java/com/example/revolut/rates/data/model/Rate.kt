package com.example.revolut.rates.data.model

data class Rate (
    var code: String?,
    var rate: Double?
) {
    companion object {
        val EMPTY = Rate("", 0.0)
    }
}