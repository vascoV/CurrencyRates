package com.example.revolut.rates.di

import com.example.revolut.rates.data.RatesApi
import com.example.revolut.rates.data.repository.CurrenciesRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule (val retrofit: Retrofit) {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return retrofit
    }

    @Provides
    @Singleton
    fun provideRatesApi(): RatesApi {
        return retrofit.create(RatesApi::class.java)
    }

    @Provides
    fun provideCurrenciesRepository() : CurrenciesRepository {
        return  CurrenciesRepository()
    }
}