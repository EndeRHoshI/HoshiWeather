package com.hoshi.weather.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoshi.weather.AppViewModel
import com.hoshi.weather.model.city.GeoBean
import com.hoshi.weather.utils.getWeatherIcon
import kotlinx.coroutines.launch

/**
 * 查找城市页面
 */
@Composable
fun SearchCity(
    modifier: Modifier,
    appViewModel: AppViewModel,
    backClick: () -> Unit
) {
    var inputText by rememberSaveable { mutableStateOf("") }
    val showDialog = rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(inputText) {
        if (inputText.isNotBlank()) {
            appViewModel.searchCity(inputText)
        } else {
            appViewModel.searchCity()
        }
    }

    val locationListData by appViewModel.locationListData.collectAsState(arrayListOf())
    val topLocationListData by appViewModel.topLocationListData.collectAsState(arrayListOf())

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = backClick) {
                Icon(Icons.AutoMirrored.Sharp.ArrowBack, "")
            }
            Box(
                modifier = Modifier.weight(1f).padding(end = 10.dp).height(30.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center,
            ) {
                BasicTextField(
                    value = inputText,
                    onValueChange = {
                        inputText = it
                    },
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    textStyle = TextStyle(fontSize = 14.sp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {

                    }),
                    maxLines = 1,
                )
            }
            Text(
                if (inputText.isNotBlank()) "取消" else "编辑",
                fontSize = 15.sp,
                modifier = Modifier.clickable {
                    if (inputText.isNotBlank()) {
                        inputText = ""
                        appViewModel.clearSearchCity()
                    } else {
                        showDialog.value = true
                    }
                }
            )
            ShowDialog(showDialog, "建议", "多学一个知识点，就少说一句求人的话！", "努力", "共勉") {}
        }
        LazyColumn {
            if (locationListData.isNotEmpty()) { // 如果位置列表不为空（也就是填入的位置能够搜到结果）就加载位置列表
                items(locationListData) { location ->
                    SearchCityItem(location) {
                        backClick()
                        scope.launch {
                            appViewModel.getWeather(it)
                        }
                        appViewModel.clearSearchCity()
                    }
                }
            } else { // 如果位置列表为空（也就是没有填位置，或者填入的位置搜不到对应的结果），就加载热门城市列表
                items(topLocationListData) { location ->
                    CityItem(location) {
                        backClick()
                        scope.launch {
                            appViewModel.getWeather(it)
                        }
                    }
                }
            }
        }
    }
}

/**
 * 城市 Item，带有名称、adm1、天气图标
 */
@Composable
fun CityItem(locationBean: GeoBean.LocationBean, onChooseCity: (GeoBean.LocationBean) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().clickable { onChooseCity(locationBean) }) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("${locationBean.name}", fontSize = 15.sp, maxLines = 1)
            Text("${locationBean.adm1}${locationBean.adm2}", fontSize = 15.sp, maxLines = 1)
            Image(painter = painterResource(getWeatherIcon("100")), "", modifier = Modifier.size(30.dp))
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp))
    }
}

/**
 * 搜索城市 Item，只有一个文本框
 */
@Composable
fun SearchCityItem(
    locationBean: GeoBean.LocationBean,
    onChooseCity: (GeoBean.LocationBean) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().clickable { onChooseCity(locationBean) }) {
        val name = if (locationBean.adm2 == locationBean.name) locationBean.name else {
            "${locationBean.adm2}${locationBean.name}"
        }
        Text(
            "${locationBean.adm1}$name",
            fontSize = 15.sp,
            modifier = Modifier.padding(10.dp),
            maxLines = 1
        )
        HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp))
    }
}