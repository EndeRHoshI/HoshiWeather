package com.hoshi.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hoshiweather.app.generated.resources.Res
import hoshiweather.app.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

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

        val greeting = remember { Greeting().greet() }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painterResource(Res.drawable.compose_multiplatform), null)
            Text("Compose: $greeting")
        }
    }
}