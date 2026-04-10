package com.example.mywanandroid.data.state

sealed class RemoteResult<out T> {
    data class Success<T>(val data: T) : RemoteResult<T>()
    data class Error(val errMsg: String, val errCode: Int) : RemoteResult<Nothing>()
}
