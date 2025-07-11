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