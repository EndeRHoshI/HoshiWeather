package com.hoshi.weather.model

import com.hoshi.weather.model.air.AirNowBean
import com.hoshi.weather.model.indices.WeatherLifeIndicesBean
import com.hoshi.weather.model.weather.WeatherDailyBean
import com.hoshi.weather.model.weather.WeatherHourlyBean
import com.hoshi.weather.model.weather.WeatherNowBean

/**
 * @param nowBaseBean 当前的天气
 * @param hourlyBeanList 未来24小时的天气预报
 * @param dailyBean 当天的天气预报
 * @param dailyBeanList 未来7天的天气预报
 * @param airNowBean 空气质量
 */
data class WeatherModel(
    val nowBaseBean: WeatherNowBean.NowBaseBean? = WeatherNowBean.NowBaseBean(),
    val hourlyBeanList: List<WeatherHourlyBean.HourlyBean> = arrayListOf(),
    val dailyBean: WeatherDailyBean.DailyBean? = WeatherDailyBean.DailyBean(),
    val dailyBeanList: List<WeatherDailyBean.DailyBean> = arrayListOf(),
    val airNowBean: AirNowBean.NowBean? = AirNowBean.NowBean(),
    val weatherLifeList: List<WeatherLifeIndicesBean.WeatherLifeIndicesItem> = arrayListOf(),
    val fxLink: String? = null
)

fun WeatherModel.isEmpty(): Boolean {
    return nowBaseBean == null || hourlyBeanList.isEmpty() || dailyBean == null ||
            dailyBeanList.isEmpty() || airNowBean == null || weatherLifeList.isEmpty() || fxLink == null
}