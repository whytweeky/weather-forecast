package com.example.weatherforecastapp.repository

import com.example.weatherforecastapp.data.City
import com.example.weatherforecastapp.data.WeatherResponse
import com.example.weatherforecastapp.network.RetrofitClient

class WeatherRepository {
    suspend fun getCityCoordinates(cityName: String): City? {
        return try {
            val cities = RetrofitClient.cityApiService.getCityCoordinates(cityName)
            cities.firstOrNull()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getWeatherForecast(latitude: Double, longitude: Double): WeatherResponse? {
        return try {
            RetrofitClient.weatherApiService.getWeatherForecast(latitude, longitude)
        } catch (e: Exception) {
            null
        }
    }
}