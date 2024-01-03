package com.example.tddworkshop.network

import com.example.tddworkshop.data.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyConverterService {

    @GET("latest")
    suspend fun getLatestRates(@Query("access_key") access_key: String): CurrencyResponse
}
