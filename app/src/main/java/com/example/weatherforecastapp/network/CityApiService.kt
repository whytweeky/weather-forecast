package com.example.weatherforecastapp.network

import com.example.weatherforecastapp.data.City
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CityApiService {
    @GET("v1/city")
    suspend fun getCityCoordinates(
        @Query("name") cityName: String,
        @Header("X-Api-Key") apiKey: String = "6i/cBGFFWKTgUvRZGFJ3hw==3CTPYnPIOXs5lQjz"
    ): List<City>
}