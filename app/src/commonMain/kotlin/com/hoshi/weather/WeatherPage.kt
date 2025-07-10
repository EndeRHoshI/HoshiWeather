package com.hoshi.weather

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WeatherPage(appViewModel: AppViewModel) {

    Row(
        modifier = Modifier.fillMaxSize().padding(10.dp),
    ) {
        // 信息区域（左边）
        // LeftInformation(appViewModel, weatherModel.nowBaseBean, currentCityId)

        // 天气展示区域（右边）
        val modifier = Modifier.weight(1f).width(500.dp)
        // RightInformation(modifier, weatherModel)
    }
}