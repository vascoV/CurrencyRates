package com.example.revolut.rates.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<N>: ViewModel(), CoroutineScope {

    private var myJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + myJob

    val compositeDisposable =  CompositeDisposable()

    var notifier: WeakReference<N>? = null

    fun setNotifier(notifier: N) {
        this.notifier = WeakReference(notifier)
    }

    override fun onCleared() {
        myJob.cancel()
        compositeDisposable.clear()
        compositeDisposable.dispose()
        super.onCleared()
    }
}