package com.example.revolut.rates.common

import com.example.revolut.rates.base.BaseNotifier

interface NotifyCurrencies: BaseNotifier {

    fun newBaseNotify(base: String)

    fun updateCurrenciesRateItems(base: String, ratesMap: Map<String, Double>)
}