package com.hoshi.weather.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoshi.weather.model.weather.WeatherDailyBean
import com.hoshi.weather.utils.getWeatherIcon

@Composable
fun WeekWeather(dayBeanList: List<WeatherDailyBean.DailyBean>?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(10.dp, 0.dp)) {
            Text(
                text = "7 日天气预报",
                fontSize = 13.sp, modifier = Modifier.padding(vertical = 6.dp)
            )
            HorizontalDivider(thickness = 0.4.dp)
        }
        // 这里不用 LazyColumn 了，应该是因为数据比较少，可以直接显示
        // 如果为空直接用一个空实体类来加载，如果不为空就全部展示出来
        if (dayBeanList.isNullOrEmpty()) {
            WeekDayWeatherItem(WeatherDailyBean.DailyBean())
        } else {
            dayBeanList.forEach { dailyBean ->
                HorizontalDivider(modifier = Modifier.padding(horizontal = 10.dp), thickness = 0.4.dp)
                WeekDayWeatherItem(dailyBean)
            }
        }
    }
}

@Composable
private fun WeekDayWeatherItem(dailyBean: WeatherDailyBean.DailyBean) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = dailyBean.fxDate ?: "今天",
            modifier = Modifier
                .weight(1f)
                .padding(start = 3.dp),
            fontSize = 15.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(getWeatherIcon(dailyBean.iconDay)),
            "",
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.weight(1.2f))
        // 当天最低温
        Text(
            text = "${dailyBean.tempMin ?: "0"}°",
            modifier = Modifier.width(50.dp).padding(end = 15.dp),
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        // 气温条状图
        TemperatureChart(
            Modifier.weight(2f).fillMaxSize(),
            dailyBean.weekMin,
            dailyBean.weekMax,
            dailyBean.tempMin?.toInt() ?: -20,
            dailyBean.tempMax?.toInt() ?: 40,
            dailyBean.temp
        )
        // 当天最高温
        Text(
            text = "${dailyBean.tempMax ?: "0"}°",
            modifier = Modifier.width(55.dp).padding(start = 15.dp, end = 5.dp),
            fontSize = 15.sp,
            textAlign = TextAlign.End,
            color = Color.Black
        )
    }
}

/**
 * 气温条状图
 *
 * @param min 未来几天最低温度
 * @param max 未来几天最高温度
 * @param currentMin 这一天的最低温度
 * @param currentMax 这一天的最高温度
 * @param currentTemperature 当天当前温度
 */
@Composable
private fun TemperatureChart(
    modifier: Modifier,
    min: Int,
    max: Int,
    currentMin: Int,
    currentMax: Int,
    currentTemperature: Int = -1
) {
    val viewHeight = 10.dp
    val midY = viewHeight.value / 2 // 因为直接 y = 0 进行绘制，会让线条画在边缘，所以需要根据 strokeWidth 的一半来计算得出最终的 y
    // 取得当前最低温、最高温颜色
    val currentMinColor = getTemperatureColor(currentMin)
    val currentMaxColor = getTemperatureColor(currentMax)
    val num = max - min // 计算周温差
    Canvas(
        modifier = modifier.fillMaxWidth().height(viewHeight)
    ) {
        // 绘制底条
        drawLine(
            color = Color.Gray,
            start = Offset(midY, midY),
            end = Offset(size.width - midY, midY),
            strokeWidth = viewHeight.value,
            cap = StrokeCap.Round,
        )
        // 绘制这一天的气温
        drawLine(
            brush = Brush.horizontalGradient(
                listOf(currentMinColor, currentMaxColor)
            ),
            start = Offset(size.width / num * (currentMin - min), midY),
            end = Offset(size.width / num * (currentMax - min), midY),
            strokeWidth = viewHeight.value,
            cap = StrokeCap.Round,
        )
        // 如果是当天，则绘制当前温度
        if (currentTemperature > -100) {
            drawPoints(
                points = arrayListOf(
                    Offset(size.width / num * (currentTemperature - min), midY)
                ),
                pointMode = PointMode.Points,
                color = Color.White,
                strokeWidth = 10f,
                cap = StrokeCap.Round,
            )
        }
    }
}

/**
 * 获取不同气温的颜色值，需要动态判断
 */
private fun getTemperatureColor(temperature: Int) = when {
    temperature < -20 -> Color(red = 26, green = 92, blue = 249)
    temperature < -15 -> Color(red = 16, green = 103, blue = 255)
    temperature < -10 -> Color(red = 28, green = 122, blue = 254)
    temperature < -5 -> Color(red = 52, green = 151, blue = 229)
    temperature < 0 -> Color(red = 65, green = 174, blue = 250)
    temperature < 5 -> Color(red = 86, green = 201, blue = 205)
    temperature < 10 -> Color(red = 86, green = 203, blue = 299)
    temperature < 15 -> Color(red = 151, green = 201, blue = 142)
    temperature < 20 -> Color(red = 247, green = 196, blue = 34)
    temperature < 25 -> Color(red = 209, green = 123, blue = 11)
    temperature < 30 -> Color(red = 253, green = 138, blue = 11)
    else -> Color(red = 248, green = 60, blue = 30)
}