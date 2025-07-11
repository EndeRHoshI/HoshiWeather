package com.hoshi.weather.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hoshi.weather.model.weather.WeatherNowBean
import androidx.compose.ui.unit.sp
import com.hoshi.weather.AppViewModel
import com.hoshi.weather.utils.getTimeNameForObs

/**
 * 天气详情
 */
@Composable
fun WeatherDetails(
    modifier: Modifier,
    nowBaseBean: WeatherNowBean.NowBaseBean?,
    onAddClick: () -> Unit, // 点击添加按钮时间
    onRefreshClick: () -> Unit // 刷新事件
) {

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onAddClick) {
                Icon(Icons.Sharp.Add, "")
            }
            Text(nowBaseBean?.city ?: AppViewModel.DEFAULT_CITY, fontSize = 14.sp)
            Spacer(modifier = Modifier.weight(1f))

            Text(nowBaseBean?.obsTime?.getTimeNameForObs() ?: "现在", fontSize = 14.sp)
            IconButton(onClick = onRefreshClick) {
                Icon(Icons.Sharp.Refresh, "")
            }
        }
    }

}