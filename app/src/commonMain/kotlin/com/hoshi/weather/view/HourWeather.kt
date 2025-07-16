package com.hoshi.weather.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoshi.weather.model.weather.WeatherHourlyBean
import com.hoshi.weather.utils.getTimeName
import com.hoshi.weather.utils.getWeatherIcon

@Composable
fun HourWeather(hourlyBeanList: List<WeatherHourlyBean.HourlyBean>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(10.dp, 0.dp)) {
            Text(
                text = "24 小时天气预报",
                fontSize = 13.sp, modifier = Modifier.padding(vertical = 6.dp)
            )
            HorizontalDivider(thickness = 0.4.dp)
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(5.dp)
            ) {
                items(hourlyBeanList) { hourlyBean ->
                    HourWeatherItem(hourlyBean)
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
private fun HourWeatherItem(hourlyBean: WeatherHourlyBean.HourlyBean) {
    Column(
        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 时间
        Text(
            text = hourlyBean.fxTime?.getTimeName() ?: "现在",
            fontSize = 14.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(15.dp)) // 分割线
        // 图标
        Image(
            painter = painterResource(getWeatherIcon(hourlyBean.icon)),
            "",
            modifier = Modifier.size(20.dp)
        )
        // 温度
        Text(
            text = "${hourlyBean.temp}°",
            modifier = Modifier.padding(top = 15.dp),
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}