package com.example.revolut.rates.common

fun String.StringToDouble(): Double
{
    if (isNullOrBlank())
        return 0.0

    return toDouble()
}

/**
 * Extension to exclude the last character of the string
 */
fun String.removeLastChar(): String
{
    if (isNullOrBlank())
        return ""

    return this.substring(0, this.length - 1)
}