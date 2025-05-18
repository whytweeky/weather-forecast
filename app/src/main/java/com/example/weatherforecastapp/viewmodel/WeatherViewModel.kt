package com.example.weatherforecastapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapp.data.City
import com.example.weatherforecastapp.data.WeatherResponse
import com.example.weatherforecastapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val repository = WeatherRepository()
    val selectedCity = mutableStateOf("Moscow")
    val cities = listOf("Moscow", "London", "New York", "Tokyo", "Paris", )
    val weatherData = mutableStateOf<WeatherResponse?>(null)
    val errorMessage = mutableStateOf<String?>(null)
    val isLoading = mutableStateOf(false)

    fun fetchWeather() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                val city = repository.getCityCoordinates(selectedCity.value)
                if (city != null) {
                    val weather = repository.getWeatherForecast(city.latitude, city.longitude)
                    weatherData.value = weather
                } else {
                    errorMessage.value = "City not found"
                }
            } catch (e: Exception) {
                errorMessage.value = "Failed to fetch data: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}