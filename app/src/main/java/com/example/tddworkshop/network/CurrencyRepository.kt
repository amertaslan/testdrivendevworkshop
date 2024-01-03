package com.example.tddworkshop.network

import com.example.tddworkshop.data.CurrencyResponse
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val service: CurrencyConverterService
) {

    private val API_KEY = "935196cda365b360e40ec291fbab2056"

    suspend fun getLatestRates(): CurrencyResponse {
        return service.getLatestRates(API_KEY)
    }
}
