package com.hoshi.weather.network

import com.hoshi.weather.model.Lang
import com.hoshi.weather.model.city.GeoBean
import com.hoshi.weather.model.city.TopGeoBean
import com.hoshi.weather.network.service.CityLookupService

/**
 * 天气远端仓库，类似 Repository 那种
 */
object PlayWeatherNetwork {

    private var language: Lang = Lang.ZH_HANS

    private val cityLookupService = ServiceCreator.createCity(CityLookupService::class.java)

    suspend fun getCityLookup(location: String): GeoBean {
        return cityLookupService.getCityLookup(location = location, lang = language.code)
    }

    suspend fun getCityTop(): TopGeoBean {
        return cityLookupService.getCityTop(lang = language.code)
    }

}