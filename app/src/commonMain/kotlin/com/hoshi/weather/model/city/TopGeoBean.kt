package com.hoshi.weather.model.city

import com.hoshi.weather.model.Refer
import com.hoshi.weather.model.city.GeoBean.LocationBean

/**
 * 热门城市实体类
 */
data class TopGeoBean(
    val code: String? = null,
    val refer: Refer? = null,
    val topCityList: List<LocationBean>? = null
)