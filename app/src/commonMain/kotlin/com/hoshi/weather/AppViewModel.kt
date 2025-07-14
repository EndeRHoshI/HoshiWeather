package com.hoshi.weather

import com.hoshi.weather.model.WeatherModel
import com.hoshi.weather.model.city.GeoBean
import com.hoshi.weather.model.weather.WeatherDailyBean
import com.hoshi.weather.model.weather.WeatherNowBean
import com.hoshi.weather.network.PlayWeatherNetwork
import com.hoshi.weather.utils.DataStoreUtils
import com.hoshi.weather.utils.getDateWeekName
import com.hoshi.weather.utils.lifePrefix
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
     * 通过接口获取天气信息，并且存入数据库中
     *
     * @param locationModel 位置实体类
     */
    suspend fun getWeather(locationModel: GeoBean.LocationBean) {
        val location = locationModel.id ?: DEFAULT_CITY_ID
        getWeather(location)
        DataStoreUtils.putData(
            CURRENT_CITY,
            if (location == DEFAULT_CITY_ID) DEFAULT_CITY else locationModel.name
        )
        DataStoreUtils.putData(CURRENT_CITY_ID, location)
    }

    /**
     * 通过接口获取天气信息
     *
     * @param location 位置
     */
    suspend fun getWeather(location: String = DEFAULT_CITY_ID) {
        println("开始查询天气接口")
        val weatherNow = playWeatherNetwork.getWeatherNow(location)
        // 这块由于这两个接口有问题，和风天气的jar包问题，提交反馈人家说没问题。。qtmd。
        // 目前发现在S版本上有问题，R中没有发现
        val weather24Hour = playWeatherNetwork.getWeather24Hour(location)
        val weather7Day = playWeatherNetwork.getWeather7Day(location)
        val airNow = playWeatherNetwork.getAirNowBean(location)
        val weatherLifeIndicesList = playWeatherNetwork.getWeatherLifeIndicesBean(location)

        buildWeekWeather(weather7Day, weatherNow)

        weatherLifeIndicesList.daily?.apply {
            get(0).imgRes = "${lifePrefix}ic_life_sport.svg"
            get(1).imgRes = "${lifePrefix}ic_life_car.svg"
            get(2).imgRes = "${lifePrefix}ic_life_clothes.svg"
            get(3).imgRes = "${lifePrefix}ic_life_uv.svg"
            get(4).imgRes = "${lifePrefix}ic_life_travel.svg"
            get(5).imgRes = "${lifePrefix}ic_life_cold.svg"
        }

        val weatherModel = WeatherModel(
            nowBaseBean = weatherNow.now,
            hourlyBeanList = weather24Hour.hourly,
            dailyBean = if (weather7Day.daily.isNotEmpty()) weather7Day.daily[0] else WeatherDailyBean.DailyBean(),
            dailyBeanList = weather7Day.daily,
            airNowBean = airNow.now,
            weatherLifeList = weatherLifeIndicesList.daily ?: arrayListOf(),
            fxLink = weatherNow.fxLink
        )

        if (_weatherModel.value == weatherModel) {
            println("weatherModel same as before. Skip it")
            return
        }
        _weatherModel.value = weatherModel

        println("hoshi 查接口了，weatherModel = $weatherModel")
    }

    /**
     * 为了构建7天天气的柱状图
     */
    private fun buildWeekWeather(
        weather7Day: WeatherDailyBean,
        weatherNow: WeatherNowBean
    ) {
        var min = Int.MAX_VALUE
        var max = Int.MIN_VALUE
        weather7Day.daily.forEach {
            val currentMin = it.tempMin?.toInt() ?: 0
            if (min > currentMin) {
                min = currentMin
            }

            val currentMax = it.tempMax?.toInt() ?: 0
            if (max < currentMax) {
                max = currentMax
            }
        }

        weather7Day.daily.forEachIndexed { index, dailyBean ->
            dailyBean.weekMin = min
            dailyBean.weekMax = max

            if (index == 0) {
                dailyBean.fxDate = "今天"
            } else {
                dailyBean.fxDate = dailyBean.fxDate?.getDateWeekName() ?: "今天"
            }

            if (dailyBean.fxDate == "今天") {
                dailyBean.temp = weatherNow.now.temp?.toInt() ?: -100
            }
        }
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