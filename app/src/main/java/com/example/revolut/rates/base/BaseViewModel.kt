package com.example.revolut.rates.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

abstract class BaseViewModel<N>: ViewModel() {

    val compositeDisposable =  CompositeDisposable()

    var notifier: WeakReference<N>? = null

    fun setNotifier(notifier: N) {
        this.notifier = WeakReference(notifier)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
        super.onCleared()
    }
}