package com.hoshi.weather.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoshi.weather.extentions.openBrowse

@Composable
fun SourceData(fxLink: String?) {
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 20.dp, bottom = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(painter = painterResource("image/ic_launcher.svg"), "", modifier = Modifier.size(15.dp))

            Spacer(modifier = Modifier.width(5.dp))

            Text(text = "数据来自和风天气", fontSize = 12.sp, modifier = Modifier.clickable {
                fxLink.openBrowse()
            })
        }

        Text(text = "程序参考 PlayWeather:玩天气", fontSize = 12.sp, modifier = Modifier.clickable {
            "https://github.com/zhujiang521/PlayWeather/tree/desktop".openBrowse()
        })
    }
}
