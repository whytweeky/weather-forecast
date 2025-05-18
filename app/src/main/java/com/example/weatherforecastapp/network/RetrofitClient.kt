package com.example.weatherforecastapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val CITY_BASE_URL = "https://api.api-ninjas.com/"
    private const val WEATHER_BASE_URL = "https://api.open-meteo.com/"

    val cityApiService: CityApiService by lazy {
        Retrofit.Builder()
            .baseUrl(CITY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CityApiService::class.java)
    }

    val weatherApiService: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }
}