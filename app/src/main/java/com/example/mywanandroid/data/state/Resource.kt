package com.example.mywanandroid.data.state

sealed class Resource<out T> {
    data class Success<T>(val data: T, val isFromCache: Boolean = false) : Resource<T>()
    data class Error(val errMsg: String, val errCode: Int? = 0) : Resource<Nothing>()
}
