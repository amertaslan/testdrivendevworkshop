package com.example.tddworkshop.di

import com.example.tddworkshop.data.CurrencyResponse
import com.example.tddworkshop.network.CurrencyConverterService
import com.example.tddworkshop.network.CurrencyRepository
import com.example.tddworkshop.network.RetrofitClient
import com.example.tddworkshop.ui.viewmodel.CurrencyViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrencyConverterModule {

    @Provides
    @Singleton
    fun provideCurrencyConverterService(): CurrencyConverterService {
        return RetrofitClient.create().create(CurrencyConverterService::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(service: CurrencyConverterService): CurrencyRepository {
        return CurrencyRepository(service)
    }

    @Provides
    @Singleton
    fun provideCurrencyResponse(): CurrencyResponse = CurrencyResponse()

    @Provides
    @Singleton
    fun provideCurrencyViewModel(repository: CurrencyRepository): CurrencyViewModel = CurrencyViewModel(repository)
}
