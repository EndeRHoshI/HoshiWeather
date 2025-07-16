package com.hoshi.weather.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoshi.weather.extentions.matchTrue
import com.hoshi.weather.model.air.AirNowBean

/**
 * 空气质量
 */
@Composable
fun AirQuality(airNowBean: AirNowBean.NowBean?) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(10.dp, 0.dp)) {
            Text(
                text = "空气质量", fontSize = 13.sp, modifier = Modifier.padding(vertical = 6.dp)
            )
            HorizontalDivider(thickness = 0.4.dp)
            Text(
                text = "${airNowBean?.aqi ?: "10"} - ${airNowBean?.category ?: "优"}",
                modifier = Modifier.padding(top = 5.dp),
                fontSize = 20.sp
            )
            Text(
                text = "当前AQI（CN）为：${airNowBean?.aqi ?: "10"}",
                modifier = Modifier.padding(top = 5.dp),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            AirQualityProgress((airNowBean?.aqi ?: "10").toInt())
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

/**
 * 空气质量图
 * 0-50	一级	优	绿色
 * 51-100	二级	良	黄色
 * 101-150	三级	轻度污染	橙色
 * 151-200	四级	中度污染	红色
 * 201-300	五级	重度污染	紫色
 * >300	六级	严重污染	褐红色
 *
 * @param aqi 空气质量数值
 */
@Composable
private fun AirQualityProgress(aqi: Int) {
    val aqiValue = (aqi < 500).matchTrue(aqi, 500)
    val viewHeight = 20.dp
    val midY = viewHeight.value / 2
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(viewHeight)
    ) {
        // 画一根圆角的粗线条
        drawLine(
            brush = Brush.linearGradient(
                0.0f to Color(red = 139, green = 195, blue = 74),
                0.1f to Color(red = 255, green = 239, blue = 59),
                0.2f to Color(red = 255, green = 152, blue = 0),
                0.3f to Color(red = 244, green = 67, blue = 54),
                0.4f to Color(red = 156, green = 39, blue = 176),
                1.0f to Color(red = 143, green = 0, blue = 0),
            ),
            start = Offset(midY, midY),
            end = Offset(size.width - midY, midY),
            strokeWidth = viewHeight.value,
            cap = StrokeCap.Round,
        )
        // 画线上的圆点
        drawPoints(
            points = arrayListOf(
                Offset(size.width / 500 * aqiValue, midY)
            ),
            pointMode = PointMode.Points,
            color = Color.White,
            strokeWidth = viewHeight.value,
            cap = StrokeCap.Round,
        )
    }
}