package com.hoshi.weather.network.service

import WEATHER_KEY
import com.hoshi.weather.model.air.AirNowBean
import com.hoshi.weather.model.city.GeoBean
import com.hoshi.weather.model.city.TopGeoBean
import com.hoshi.weather.model.indices.WeatherLifeIndicesBean
import com.hoshi.weather.model.weather.WeatherDailyBean
import com.hoshi.weather.model.weather.WeatherHourlyBean
import com.hoshi.weather.model.weather.WeatherNowBean
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 接口声明
 */
interface WeatherService {

    /**
     * 实时天气
     *
     * @param key 用户认证key
     * @param location 需要查询地区的LocationID或以英文逗号分隔的经度,纬度坐标
     * @param lang 多语言设置，默认中文
     *
     * 实时温度、体感温度、风力风向、相对湿度、大气压强、降水量、能见度、露点温度、云量等数据。
     */
    @GET("/v7/weather/now")
    suspend fun getWeatherNow(
        @Query("key") key: String = WEATHER_KEY,
        @Query("location") location: String,
        @Query("lang") lang: String
    ): WeatherNowBean


    /**
     * 未来24小时
     *
     * @param key 用户认证key
     * @param location 需要查询地区的LocationID或以英文逗号分隔的经度,纬度坐标
     * @param lang 多语言设置，默认中文
     *
     * 温度、天气状况、风力、风速、风向、相对湿度、大气压强、降水概率、露点温度、云量。
     */
    @GET("/v7/weather/24h")
    suspend fun getWeather24Hour(
        @Query("key") key: String = WEATHER_KEY,
        @Query("location") location: String,
        @Query("lang") lang: String
    ): WeatherHourlyBean

    /**
     * 未来3天
     *
     * @param key 用户认证key
     * @param location 需要查询地区的LocationID或以英文逗号分隔的经度,纬度坐标
     * @param lang 多语言设置，默认中文
     *
     * 日出日落、月升月落、最高最低温度、天气白天和夜间状况、风力、风速、风向、相对湿度、大气压强、降水量、降水概率、露点温度、紫外线强度、能见度等。
     */
    @GET("/v7/weather/3d")
    suspend fun getWeather3Day(
        @Query("key") key: String = WEATHER_KEY,
        @Query("location") location: String,
        @Query("lang") lang: String
    ): WeatherDailyBean

    /**
     * 未来7天
     *
     * @param key 用户认证key
     * @param location 需要查询地区的LocationID或以英文逗号分隔的经度,纬度坐标
     * @param lang 多语言设置，默认中文
     *
     * 日出日落、月升月落、最高最低温度、天气白天和夜间状况、风力、风速、风向、相对湿度、大气压强、降水量、降水概率、露点温度、紫外线强度、能见度等。
     */
    @GET("/v7/weather/7d")
    suspend fun getWeather7Day(
        @Query("key") key: String = WEATHER_KEY,
        @Query("location") location: String,
        @Query("lang") lang: String
    ): WeatherDailyBean

    /**
     * 实时空气质量
     *
     * @param key 用户认证key
     * @param location 需要查询地区的LocationID或以英文逗号分隔的经度,纬度坐标
     * @param lang 多语言设置，默认中文
     *
     * 和风天气生活指数API接口为中国和海外城市提供详细的生活指数实况和预报数据，包括：
    中国天气生活指数：舒适度指数、洗车指数、穿衣指数、感冒指数、运动指数、旅游指数、紫外线指数、空气污染扩散条件指数、
    空调开启指数、过敏指数、太阳镜指数、化妆指数、晾晒指数、交通指数、钓鱼指数、防晒指数
    海外天气生活指数：运动指数、洗车指数、紫外线指数、钓鱼指数
     *
     */
    @GET("/v7/indices/1d")
    suspend fun getWeatherLifeIndicesBean(
        @Query("key") key: String = WEATHER_KEY,
        @Query("location") location: String,
        @Query("lang") lang: String,
        @Query("type") type: String
    ): WeatherLifeIndicesBean

    /**
     * 天气生活指数
     *
     * @param key 用户认证key
     * @param location 需要查询地区的LocationID或以英文逗号分隔的经度,纬度坐标
     * @param lang 多语言设置，默认中文
     *
     * 和风天气生活指数API接口为中国和海外城市提供详细的生活指数实况和预报数据，包括：
    中国天气生活指数：舒适度指数、洗车指数、穿衣指数、感冒指数、运动指数、旅游指数、紫外线指数、空气污染扩散条件指数、
    空调开启指数、过敏指数、太阳镜指数、化妆指数、晾晒指数、交通指数、钓鱼指数、防晒指数
    海外天气生活指数：运动指数、洗车指数、紫外线指数、钓鱼指数
     *
     */
    @GET("/v7/air/now")
    suspend fun getAirNowBean(
        @Query("key") key: String = WEATHER_KEY,
        @Query("location") location: String,
        @Query("lang") lang: String
    ): AirNowBean

    /**
     * 城市信息查询
     *
     * @param key 用户认证key
     * @param location 需要查询地区的LocationID或以英文逗号分隔的经度,纬度坐标
     * @param lang 多语言设置，默认中文
     *
     * 天气数据是基于地理位置的数据，因此获取天气之前需要先知道具体的位置信息。使用城市搜索，可获取到该城市的基本信息，
     * 包括城市的Location ID（你需要这个ID去查询天气），多语言名称、经纬度、时区、海拔、Rank值、归属上级行政区域、所在行政区域等。
     * 另外，城市搜索也可以帮助你在你的APP中实现模糊搜索，用户只需要输入1-2个字即可获得结果。
     *
     */
    @GET("/geo/v2/city/lookup")
    suspend fun getCityLookup(
        @Query("key") key: String = WEATHER_KEY,
        @Query("location") location: String,
        @Query("lang") lang: String
    ): GeoBean

    /**
     * 热门城市查询
     *
     * @param key 用户认证key
     * @param lang 多语言设置，默认中文
     *
     * 天气数据是基于地理位置的数据，因此获取天气之前需要先知道具体的位置信息。使用城市搜索，可获取到该城市的基本信息，
     * 包括城市的Location ID（你需要这个ID去查询天气），多语言名称、经纬度、时区、海拔、Rank值、归属上级行政区域、所在行政区域等。
     * 另外，城市搜索也可以帮助你在你的APP中实现模糊搜索，用户只需要输入1-2个字即可获得结果。
     *
     */
    @GET("/geo/v2/city/top")
    suspend fun getCityTop(
        @Query("key") key: String = WEATHER_KEY,
        @Query("lang") lang: String
    ): TopGeoBean

}