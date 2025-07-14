package com.hoshi.weather.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private val yyyyMMdd = SimpleDateFormat("yyyy-MM-dd'T'HH:mm+08:00", Locale.getDefault())

/**
 * 获取指定时间为几点
 *
 * time 年月日 2013-12-30T13:00+08:00
 * @return 09:25
 */
fun String?.getTimeNameForObs(): String {
    val calendar = Calendar.getInstance()
    // HH 为 24 小时 hh 为 12 小时
    calendar.time = yyyyMMdd.parse(this) ?: Date()
    return "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
}

private val calendar = Calendar.getInstance()

/**
 * 获取指定年月日的周几
 *
 * this 年月日 2013-12-30
 * @return 周几
 */
fun String.getDateWeekName(): String {
    val dateArray = split("-")
    val todayWeek = calendar.get(Calendar.DAY_OF_WEEK)
    calendar.clear()
    calendar.set(dateArray[0].toInt(), dateArray[1].toInt() - 1, dateArray[2].toInt())
    val weeks = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
    var weekIndex: Int = calendar.get(Calendar.DAY_OF_WEEK) - 1
    if (weekIndex < 0) {
        weekIndex = 0
    }
    return if (todayWeek == calendar.get(Calendar.DAY_OF_WEEK)) {
        "今天"
    } else {
        weeks[weekIndex]
    }
}