package com.hoshi.weather.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hoshi.weather.AppViewModel
import com.hoshi.weather.model.weather.WeatherNowBean
import kotlinx.coroutines.launch

/**
 * 主页面左边
 */
@Composable
fun LeftInformation(
    appViewModel: AppViewModel,
    nowBaseBean: WeatherNowBean.NowBaseBean?,
    currentCityId: String
) {
    var showSearch by rememberSaveable { mutableStateOf(false) } // 是否展示搜索框
    val currentCity by appViewModel.currentCity.collectAsState(AppViewModel.DEFAULT_CITY) // 当前城市
    val scope = rememberCoroutineScope() // 协程作用域
    Box(
        Modifier.fillMaxHeight().width(300.dp).padding(end = 10.dp)
    ) {
        WeatherDetails(
            Modifier.fillMaxSize()
                .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
            nowBaseBean.apply {
                nowBaseBean?.city = currentCity
            },
            onAddClick = {
                showSearch = true
            },
            onRefreshClick = {
                scope.launch {
                    appViewModel.getWeather(currentCityId)
                }
            }
        )
        AnimatedVisibility(
            visible = showSearch,
            enter = slideInHorizontally(),
            exit = slideOutHorizontally()
        ) {
            SearchCity(
                Modifier.fillMaxSize()
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                appViewModel
            ) {
                showSearch = false
            }
        }
    }
}