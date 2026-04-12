package com.example.mywanandroid.common.utils

import android.graphics.Color
import kotlin.random.Random

fun generateRandomColor(): Int {
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)
    val alpha = 255 // 不透明度，可根据需要调整

    return Color.argb(alpha, red, green, blue)
}