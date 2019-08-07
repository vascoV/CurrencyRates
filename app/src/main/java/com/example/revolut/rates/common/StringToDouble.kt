package com.example.revolut.rates.common

fun String.StringToDouble(): Double
{
    if (isNullOrBlank())
        return 0.0

    return toDouble()
}
