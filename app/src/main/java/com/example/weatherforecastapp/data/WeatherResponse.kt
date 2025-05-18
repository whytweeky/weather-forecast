package com.example.weatherforecastapp.data

data class WeatherResponse(
    val hourly: Hourly
)

data class Hourly(
    val time: List<String>,
    val temperature_2m: List<Double>
)