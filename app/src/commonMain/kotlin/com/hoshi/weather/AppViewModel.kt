package com.hoshi.weather

import com.hoshi.weather.model.WeatherModel
import com.hoshi.weather.model.city.GeoBean
import com.hoshi.weather.network.PlayWeatherNetwork
import com.hoshi.weather.utils.DataStoreUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * 整个 App 的 ViewModel
 */
class AppViewModel {

    companion object {
        private const val CURRENT_CITY = "CURRENT_CITY"
        const val DEFAULT_CITY = "北京" // 默认城市
        private const val CURRENT_CITY_ID = "CURRENT_CITY_ID"
        const val DEFAULT_CITY_ID = "CN101010100" // 默认城市 ID
    }

    private val playWeatherNetwork = PlayWeatherNetwork

    /**
     * 当前天气，可见性是 private，不允许 set，但是另外暴露了 get
     */
    private val _weatherModel = MutableStateFlow(WeatherModel())
    val weatherModelData: Flow<WeatherModel>
        get() = _weatherModel

    /**
     * 获取天气信息
     *
     * @param locationModel 位置实体类
     */
    suspend fun getWeather(locationModel: GeoBean.LocationBean) {
        val location = locationModel.id ?: DEFAULT_CITY_ID
        getWeather(location)
        /*DataStoreUtils.putData(
            CURRENT_CITY,
            if (location == DEFAULT_CITY_ID) DEFAULT_CITY else locationModel.name
        )
        DataStoreUtils.putData(CURRENT_CITY_ID, location)*/
    }

    /**
     * 获取天气信息
     *
     * @param location 位置
     */
    suspend fun getWeather(location: String = DEFAULT_CITY_ID) {

    }

    /**
     * 当前城市及当前城市 ID
     */
    val currentCity = DataStoreUtils.getData(CURRENT_CITY, DEFAULT_CITY)
    val currentCityId = DataStoreUtils.getData(CURRENT_CITY_ID, DEFAULT_CITY_ID)

    /**
     * 位置列表
     */
    private val _locationModel = MutableStateFlow(listOf<GeoBean.LocationBean>())
    val locationListData: Flow<List<GeoBean.LocationBean>>
        get() = _locationModel

    /**
     * 查找城市
     * @param inputText 输入的内容
     */
    suspend fun searchCity(inputText: String) {
        val geoBean = playWeatherNetwork.getCityLookup(inputText)
        val locationBeanList = geoBean.location
        if (!locationBeanList.isNullOrEmpty()) {
            if (_locationModel.value == locationBeanList) {
                println("locationModel same as before. Skip it")
            }
            _locationModel.value = locationBeanList
        }
    }

    /**
     * 清除城市搜索
     */
    fun clearSearchCity() {
        _locationModel.value = arrayListOf()
    }

    /**
     * 热门城市列表
     */
    private val _topLocationModel = MutableStateFlow(listOf<GeoBean.LocationBean>())
    val topLocationListData: Flow<List<GeoBean.LocationBean>>
        get() = _topLocationModel

    suspend fun searchCity() {
        val geoBean = playWeatherNetwork.getCityTop()
        val locationBeanList = geoBean.topCityList
        if (!locationBeanList.isNullOrEmpty()) {
            if (_topLocationModel.value == locationBeanList) {
                println("topLocationModel same as before. Skip it")
            }
            _topLocationModel.value = locationBeanList
        }
    }



}