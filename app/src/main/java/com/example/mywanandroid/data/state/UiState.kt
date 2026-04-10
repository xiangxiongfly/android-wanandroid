package com.example.mywanandroid.data.state

sealed class UiState<out T> {
    // 初始状态
    object Idle : UiState<Nothing>()

    // 加载中
    object Loading : UiState<Nothing>()

    // 成功
    data class Success<out T>(val data: T) : UiState<T>()

    // 空数据
    object Empty : UiState<Nothing>()

    // 失败
    data class Error(val errMsg: String, val errCode: Int? = null) : UiState<Nothing>()
}