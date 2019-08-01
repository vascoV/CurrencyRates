package com.example.revolut.rates.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.revolut.rates.data.repository.CurrenciesRepository
import com.example.revolut.rates.di.Injector
import kotlinx.coroutines.*
import javax.inject.Inject

class CurrenciesViewModel : ViewModel() {

    @Inject
    lateinit var repository: CurrenciesRepository

    init {
        Injector.appComponent.inject(this)
    }

    var currencies: MutableLiveData<List<Pair<String, Double>>> = MutableLiveData()

    fun fetchCurrencies(base: String): LiveData<List<Pair<String, Double>>> {

        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getCurrenciesList(base)

            withContext(Dispatchers.Main){
                currencies.postValue(result.toList())
            }
        }

        return currencies
    }
}
