package com.example.mywanandroid.data.state

import androidx.annotation.IntDef

const val LOAD_TYPE_INITIAL = 0
const val LOAD_TYPE_REFRESH = 1
const val LOAD_TYPE_LOAD_MORE = 2

@IntDef(LOAD_TYPE_INITIAL, LOAD_TYPE_REFRESH, LOAD_TYPE_LOAD_MORE)
annotation class LoadType

sealed class ListUiState<out T> {
    // 初始状态
    object Idle : ListUiState<Nothing>()

    // 加载中
    object Loading : ListUiState<Nothing>()
    object Refreshing : ListUiState<Nothing>()
    object LoadingMore : ListUiState<Nothing>()

    // 加载成功
    data class Success<T>(
        @LoadType val loadType: Int,
        val items: List<T>,
    ) : ListUiState<T>()

    // 加载失败
    data class Error(
        @LoadType val loadType: Int,
        val errMsg: String,
        val errCode: Int? = null,
    ) : ListUiState<Nothing>()

    // 空数据
    object Empty : ListUiState<Nothing>()
}