package com.hoshi.weather.utils

const val weatherPrefix: String = "image/weather/"
const val lifePrefix = "image/life/"

/**
 * 获取天气图标
 *
 * @param weather 天气状况代码
 * @return 天气icon
 */
fun getWeatherIcon(weather: String?): String {
    if (weather == null) return "${weatherPrefix}x_sunny.svg"
    return when (weather) {
        "100" -> "${weatherPrefix}x_sunny.svg"
        "150" -> "${weatherPrefix}x_night_sunny.svg"
        "101", "102", "104", "151", "152" -> "${weatherPrefix}x_cloudy.svg"
        "103" -> "${weatherPrefix}x_day_cloudy.svg"
        "153" -> "${weatherPrefix}x_night_cloudy.svg"
        "300", "301" -> "${weatherPrefix}x_shower.svg"
        "302", "303", "304" -> "${weatherPrefix}x_thunder_rain.svg"
        "305", "308", "309" -> "${weatherPrefix}x_small_rain.svg"
        "306", "350", "351", "399" -> "${weatherPrefix}x_mid_rain.svg"
        "307" -> "${weatherPrefix}x_big_rain.svg"
        "400", "401", "402", "403", "404", "405", "406", "407", "408", "409", "410", "456", "457", "499" -> "${weatherPrefix}x_snow.svg"
        "500", "501", "502", "503", "504", "507", "508", "509", "510", "511", "512", "513", "514", "515" -> "${weatherPrefix}x_fog.svg"
        else -> "${weatherPrefix}x_sunny.svg"
    }
}