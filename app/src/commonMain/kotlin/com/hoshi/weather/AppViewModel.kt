package com.hoshi.weather

import com.hoshi.weather.model.WeatherModel
import com.hoshi.weather.utils.DataStoreUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * 整个 App 的 ViewModel
 */
class AppViewModel {

    companion object {
        private const val CURRENT_CITY = "CURRENT_CITY"
        private const val DEFAULT_CITY = "北京" // 默认城市
        private const val CURRENT_CITY_ID = "CURRENT_CITY_ID"
        const val DEFAULT_CITY_ID = "CN101010100" // 默认城市 ID
    }

    /**
     * 当前天气，可见性是 private，不允许 set，但是另外暴露了 get
     */
    private val _weatherModel = MutableStateFlow(WeatherModel())
    val weatherModelData: Flow<WeatherModel>
        get() = _weatherModel

    /**
     * 当前城市及当前城市 ID
     */
    val currentCity = DataStoreUtils.getData(CURRENT_CITY, DEFAULT_CITY)
    val currentCityId = DataStoreUtils.getData(CURRENT_CITY_ID, DEFAULT_CITY_ID)

}