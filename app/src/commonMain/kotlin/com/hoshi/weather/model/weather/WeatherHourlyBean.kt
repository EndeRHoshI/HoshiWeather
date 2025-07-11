package com.hoshi.weather.model.weather

import com.hoshi.weather.model.Refer
import java.util.ArrayList

/**
 * 每小时天气实体类
 */
data class WeatherHourlyBean(
    val fxLink: String? = null,
    val code: String? = null,
    val refer: Refer? = null,
    val updateTime: String? = null,
    val hourly: List<HourlyBean> = ArrayList()
) {

    data class HourlyBean(
        var temp: String? = null,
        var icon: String? = null,
        val wind360: String? = null,
        val windDir: String? = null,
        val pressure: String? = null,
        var fxTime: String? = null,
        val pop: String? = null,
        val cloud: String? = null,
        val precip: String? = null,
        val dew: String? = null,
        val humidity: String? = null,
        val text: String? = null,
        val windSpeed: String? = null,
        val windScale: String? = null
    )
}