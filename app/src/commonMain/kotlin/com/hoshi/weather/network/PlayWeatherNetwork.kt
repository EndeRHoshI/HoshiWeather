package com.hoshi.weather.network

import com.hoshi.weather.model.Lang
import com.hoshi.weather.model.air.AirNowBean
import com.hoshi.weather.model.city.GeoBean
import com.hoshi.weather.model.city.TopGeoBean
import com.hoshi.weather.model.indices.WeatherLifeIndicesBean
import com.hoshi.weather.model.weather.WeatherDailyBean
import com.hoshi.weather.model.weather.WeatherHourlyBean
import com.hoshi.weather.model.weather.WeatherNowBean
import com.hoshi.weather.network.service.WeatherService

/**
 * 天气远端仓库，类似 Repository 那种
 */
object PlayWeatherNetwork {

    private var language: Lang = Lang.ZH_HANS

    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    suspend fun getWeatherLifeIndicesBean(
        location: String
    ): WeatherLifeIndicesBean = weatherService.getWeatherLifeIndicesBean(location = location, lang = language.code, type = "1,2,3,5,6,9")

    suspend fun getCityLookup(location: String): GeoBean = runCatching { // 这个接口，输入不正确参数，有时会报 400，加个捕获
        weatherService.getCityLookup(location = location, lang = language.code)
    }.getOrNull() ?: GeoBean()

    suspend fun getCityTop(): TopGeoBean = weatherService.getCityTop(lang = language.code)

    suspend fun getWeatherNow(location: String): WeatherNowBean = weatherService.getWeatherNow(location = location, lang = language.code)

    suspend fun getWeather24Hour(
        location: String
    ): WeatherHourlyBean = weatherService.getWeather24Hour(location = location, lang = language.code)

    suspend fun getWeather3Day(location: String): WeatherDailyBean =
        weatherService.getWeather3Day(location = location, lang = language.code)

    suspend fun getWeather7Day(location: String): WeatherDailyBean =
        weatherService.getWeather7Day(location = location, lang = language.code)

    suspend fun getAirNowBean(location: String): AirNowBean = weatherService.getAirNowBean(location = location, lang = language.code)

}