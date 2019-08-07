package com.example.revolut.rates.base

interface BaseNotifier {

    fun showErrorMessage(message: String?)

    fun isLoading(isLoading: Boolean)
}