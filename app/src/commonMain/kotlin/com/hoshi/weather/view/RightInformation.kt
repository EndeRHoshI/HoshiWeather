package com.hoshi.weather.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hoshi.weather.model.WeatherModel

@Composable
fun RightInformation(
    modifier: Modifier,
    weatherModel: WeatherModel
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AirQuality(weatherModel.airNowBean) // 当前空气质量
        HourWeather(weatherModel.hourlyBeanList) // 未来 24 小时天气预报
        WeekWeather(weatherModel.dailyBeanList) // 未来 7 天天气预报
        DayWeatherContent(weatherModel) // 当天具体天气数值
        SunriseSunsetContent(weatherModel.dailyBean) // 日出日落
        LifeWeatherContent(weatherModel.weatherLifeList) // 当天生活指数
        SourceData(weatherModel.fxLink) // 数据来源
    }
}