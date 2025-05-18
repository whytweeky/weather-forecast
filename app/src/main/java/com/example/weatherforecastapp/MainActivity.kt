package com.example.weatherforecastapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.weatherforecastapp.ui.theme.WeatherForecastAppTheme
import com.example.weatherforecastapp.viewmodel.WeatherViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherForecastAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherScreen()
                }
            }
        }
    }
}

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Weather Forecast",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Выпадающее меню для выбора города
        Box {
            OutlinedButton(onClick = { expanded = true }) {
                Text(viewModel.selectedCity.value)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                viewModel.cities.forEach { city ->
                    DropdownMenuItem(
                        text = { Text(city) },
                        onClick = {
                            viewModel.selectedCity.value = city
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка для загрузки прогноза
        Button(onClick = { viewModel.fetchWeather() }) {
            Text("Get Forecast")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Индикатор загрузки
        if (viewModel.isLoading.value) {
            CircularProgressIndicator()
        }

        // Сообщение об ошибке
        viewModel.errorMessage.value?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(8.dp)
            )
        }

        // Данные прогноза
        viewModel.weatherData.value?.let { weather ->
            LazyColumn {
                items(weather.hourly.time.zip(weather.hourly.temperature_2m)) { (time, temp) ->
                    WeatherItem(time = time, temperature = temp)
                }
            }
        }
    }
}

@Composable
fun WeatherItem(time: String, temperature: Double) {
    // Парсим строку времени из формата API (yyyy-MM-dd'T'HH:mm)
    val originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    val dateTime = LocalDateTime.parse(time, originalFormatter)

    // Форматируем в нужный формат (dd-MM-yyyy HH:mm)
    val targetFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    val formattedTime = dateTime.format(targetFormatter)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formattedTime,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "${temperature}°C",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End
            )
        }
    }
}