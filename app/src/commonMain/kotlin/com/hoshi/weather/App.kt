package com.hoshi.weather

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*

@Composable
fun App() {
    val appViewModel = AppViewModel()
    MaterialTheme {
        WeatherPage(appViewModel)
    }
}