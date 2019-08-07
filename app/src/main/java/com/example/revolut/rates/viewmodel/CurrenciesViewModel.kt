package com.example.revolut.rates.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.revolut.rates.base.BaseViewModel
import com.example.revolut.rates.data.model.CurrencyResponse
import com.example.revolut.rates.data.repository.CurrenciesRepository
import com.example.revolut.rates.di.Injector
import com.example.revolut.rates.common.NotifyCurrencies
import com.example.revolut.rates.common.RETRIES
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CurrenciesViewModel : BaseViewModel<NotifyCurrencies>() {

    var currencies = MutableLiveData<CurrencyResponse>()
    var baseCurrency = MutableLiveData<String>()

    @Inject
    lateinit var repository: CurrenciesRepository

    init {
        Injector.appComponent.inject(this)

        baseCurrency.observeForever(baseCurrencyObserver())
        currencies.observeForever(latestCurrencyObserver())
    }

    private fun baseCurrencyObserver() = Observer<String> {
        compositeDisposable.clear()
        fetchCurrencies(it)
    }

    private fun latestCurrencyObserver() = Observer<CurrencyResponse> {
        notifier?.get()?.updateCurrenciesRateItems(it.base, it.rates)
    }

    private fun fetchCurrencies(base: String) {

        if (currencies.value == null) isLoading(true)

        compositeDisposable.add(
            repository.getCurrenciesList(base)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .retry(RETRIES)
                .subscribeWith(object : DisposableObserver<CurrencyResponse>() {
                    override fun onComplete() {
                        isLoading(false)
                        Log.i("Observer: ", "COMPLETE")
                    }

                    override fun onNext(t: CurrencyResponse) {
                        isLoading(false)
                        currencies.value = t
                    }

                    override fun onError(e: Throwable) {
                        isLoading(false)
                        notifier?.get()?.showErrorMessage(e.message)
                        Log.e("Observer: ", "ERROR -> ${e.toString()}")
                    }
                })
        )
    }

    private fun isLoading(loading: Boolean) {
        isLoading.value = loading
    }

    override fun onCleared() {
        currencies.removeObserver(latestCurrencyObserver())
        baseCurrency.removeObserver(baseCurrencyObserver())
        super.onCleared()
    }
}
