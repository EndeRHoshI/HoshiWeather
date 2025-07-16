package com.hoshi.weather.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoshi.weather.model.indices.WeatherLifeIndicesBean
import com.hoshi.weather.utils.lifePrefix

/**
 * 当天生活指数
 */
@Composable
fun LifeWeatherContent(weatherLifeList: List<WeatherLifeIndicesBean.WeatherLifeIndicesItem>?) {
    // TODO 后续把这里的 Card 抽成一个公共类，都是一样的代码
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "生活指数",
                fontSize = 13.sp,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 7.dp, start = 10.dp, end = 10.dp)
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 10.dp), thickness = 0.4.dp)
            // 上面一排三个
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 5.dp, end = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                val modifier = Modifier
                    .weight(1f)
                    .padding(5.dp)
                WeatherLifeItem(modifier, weatherLifeList.getWeatherLifeItem(0))
                WeatherLifeItem(modifier, weatherLifeList.getWeatherLifeItem(1))
                WeatherLifeItem(modifier, weatherLifeList.getWeatherLifeItem(2))
            }
            // 下面一排三个
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 5.dp, end = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                val modifier = Modifier
                    .weight(1f)
                    .padding(5.dp)
                WeatherLifeItem(modifier, weatherLifeList.getWeatherLifeItem(3))
                WeatherLifeItem(modifier, weatherLifeList.getWeatherLifeItem(4))
                WeatherLifeItem(modifier, weatherLifeList.getWeatherLifeItem(5))
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

private fun List<WeatherLifeIndicesBean.WeatherLifeIndicesItem>?.getWeatherLifeItem(index: Int) =
    if (this?.isEmpty() == true) null else this?.get(index)

@Composable
fun WeatherLifeItem(modifier: Modifier, item: WeatherLifeIndicesBean.WeatherLifeIndicesItem?) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // 图标
        Image(
            painter = painterResource(item?.imgRes ?: "${lifePrefix}ic_life_sport.svg"),
            contentDescription = "",
            modifier = Modifier.size(50.dp)
        )
        // 文字内容
        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(text = item?.name ?: "运动指数", fontSize = 12.sp)
            Text(
                text = item?.category ?: "较适宜",
                modifier = Modifier.padding(top = 5.dp),
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}