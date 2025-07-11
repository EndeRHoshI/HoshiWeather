package com.hoshi.weather.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

/**
 * 弹窗
 */
@Composable
fun ShowDialog(
    alertDialog: MutableState<Boolean>,
    title: String,
    content: String,
    cancelString: String = "取消",
    confirmString: String = "确定",
    onConfirmListener: () -> Unit
) {

}