package com.example.revolut.rates.data.managers

import android.content.Context
import android.net.ConnectivityManager

object NetworkManager {

    fun isNetworkAvailable(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager?.activeNetworkInfo

        return activeNetworkInfo?.isConnected ?: false

    }
}