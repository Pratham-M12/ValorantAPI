package com.example.valorantapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance{
    private const val BASE_URL = "https://valorant-api.com/"

    val api: ValorantAPIService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ValorantAPIService::class.java)
    }
}