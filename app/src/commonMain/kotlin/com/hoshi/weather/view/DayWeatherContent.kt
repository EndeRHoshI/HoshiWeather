package com.hoshi.weather.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CursorDropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoshi.weather.model.WeatherModel
import com.hoshi.weather.model.weather.DayItemData

/**
 * 当天具体天气数值
 */
@Composable
fun DayWeatherContent(
    weatherModel: WeatherModel?,
) {
    val dailyBean = weatherModel?.dailyBean
    val nowBaseBean = weatherModel?.nowBaseBean
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
    ) {
        val modifier = Modifier
            .weight(1f)
            .padding(top = 5.dp, bottom = 5.dp, end = 5.dp)
        WeatherContentItem(
            modifier = modifier,
            DayItemData(
                "能见度",
                "${nowBaseBean?.vis ?: "0"}公里",
                "当前的能见度",
                "关于能见度",
                "能见度会告诉你可以清晰地看到多远以外的物体，如建筑和山丘等。能见度测量大气透明度，不考虑光照强度或障碍物。能见度10公里及以上为良好。"
            )
        )
        WeatherContentItem(
            modifier = modifier, DayItemData(
                "气压",
                "${dailyBean?.pressure ?: "0"}百帕",
                "当前的大气压", "关于气压", "气压的显著急剧变化可用于预测天气变化。例如，" +
                        "气压降低可能表示兩雪即将来临，则可能表示天气将要好转。气压也被称为大气压或大气压强。"
            )
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        val modifier = Modifier
            .weight(1f)
            .padding(top = 5.dp, bottom = 5.dp, end = 5.dp)
        WeatherContentItem(
            modifier, DayItemData(
                "体感温度",
                "${nowBaseBean?.feelsLike ?: "0"}°",
                "与实际气温相似", "关于体感温度", "体感温度传达身体感觉有多暖或多冷，可能与实际温度不同。体感温度受湿度和风影响。"
            )

        )
        WeatherContentItem(
            modifier = modifier, DayItemData(
                "降雨",
                "${nowBaseBean?.precip ?: "0"}毫米",
                if ((nowBaseBean?.precip?.toFloat() ?: 0f) > 0f)
                    "今日有雨，记得带伞哦！"
                else "预计今日没雨", "降水强度", "未来的一个降水信息，如果由降水要注意带伞，也要注意保暖"
            )

        )
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        val modifier = Modifier
            .weight(1f)
            .padding(top = 5.dp, bottom = 5.dp, end = 5.dp)
        WeatherContentItem(
            modifier, DayItemData(
                "湿度",
                "${nowBaseBean?.humidity ?: "0"}%",
                "目前湿度正常", "关于相对湿度", "相对湿度，一般简称为湿度，是空气中含水量与空气可容纳水量的比值。" +
                        "气温越高，空气可容纳的水量就越多。若相对湿度接近100%，则意味着可能结露或起雾。"
            )
        )
        WeatherContentItem(
            modifier = modifier, DayItemData(
                "风",
                "${nowBaseBean?.windDir ?: "南风"}${nowBaseBean?.windScale ?: ""}级",
                "当前风速为${nowBaseBean?.windSpeed ?: "0"}Km/H", "关于风速和阵风", "风速的计算取短时间内的平均值。" +
                        "阵风是高于此平均值的短暂强风。阵风的持续时间通常低于 20 秒。"
            )

        )
    }
    Spacer(modifier = Modifier.height(5.dp))
}

/**
 * 天气内容 Item，一个小卡片形式
 * TODO 鼠标放上去、点击时，水波纹不是圆角的，研究下怎么解决
 */
@Composable
private fun WeatherContentItem(modifier: Modifier, data: DayItemData) {
    var showPopupWindow by remember { mutableStateOf(false) } // 是否展示弹窗
    Card(
        modifier = modifier.fillMaxWidth().clickable { showPopupWindow = true },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = data.title, fontSize = 11.sp)
            Text(
                text = data.value,
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 18.sp,
                color = Color.Black
            )
            Text(
                text = data.tip,
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 13.sp,
                color = Color.Black
            )
        }
    }
    // 点击后的弹窗
    CursorDropdownMenu(
        showPopupWindow,
        onDismissRequest = { showPopupWindow = false },
        modifier = modifier.width(300.dp).padding(horizontal = 15.dp).padding(bottom = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = data.titleDetails,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            IconButton(onClick = { showPopupWindow = false }) {
                Icon(Icons.Sharp.Close, "Close")
            }
        }
        Text(
            text = data.valueDetails,
            fontSize = 13.sp,
        )
    }
}