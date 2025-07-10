package com.hoshi.weather

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val appViewModel = AppViewModel()
    MaterialTheme {
        WeatherPage(appViewModel)
    }
}