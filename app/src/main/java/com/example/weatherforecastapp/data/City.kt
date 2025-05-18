package com.example.weatherforecastapp.data

data class City(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String,
    val population: Int,
    val is_capital: Boolean
)