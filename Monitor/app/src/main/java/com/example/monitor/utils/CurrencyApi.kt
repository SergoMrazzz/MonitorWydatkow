package com.example.monitor.utils

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CurrencyApi {
    @GET("api/exchangerates/tables/A?format=json")
    suspend fun getExchangeRates(): Response<List<ExchangeRateTable>>

    companion object {
        private const val BASE_URL = "https://api.nbp.pl/"

        fun create(): CurrencyApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(CurrencyApi::class.java)
        }
    }
}

data class ExchangeRateTable(
    val table: String,
    val no: String,
    val effectiveDate: String,
    val rates: List<Rate>
)

data class Rate(
    val currency: String,
    val code: String,
    val mid: Double
)