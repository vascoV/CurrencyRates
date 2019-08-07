package com.example.revolut.rates.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

abstract class BaseViewModel<N>: ViewModel() {

    val compositeDisposable =  CompositeDisposable()

    var notifier: WeakReference<N>? = null

    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    private val loadingObserver = Observer<Boolean> {
        (notifier?.get() as BaseNotifier?)?.isLoading(it)
    }

    init {
        isLoading.value = false
        isLoading.observeForever(loadingObserver)
    }

    fun setNotifier(notifier: N) {
        this.notifier = WeakReference(notifier)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
        super.onCleared()
    }
}