package com.example.revolut.rates.di

import com.example.revolut.rates.data.repository.CurrenciesRepository
import com.example.revolut.rates.viewmodel.CurrenciesViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(currenciesRepository: CurrenciesRepository)
    fun inject(currenciesRepository: CurrenciesViewModel)
}