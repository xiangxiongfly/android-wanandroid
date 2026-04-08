package com.example.mywanandroid.data.model

data class BaseResponse<T>(
    val errorMsg: String,
    val errorCode: Int,
    val data: T
) {
    fun isSuccessful() = errorCode == 0
}