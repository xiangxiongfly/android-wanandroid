package com.example.mywanandroid.base

import com.example.mywanandroid.data.model.BaseResponse
import com.example.mywanandroid.data.state.RemoteResult
import com.example.mywanandroid.networks.exceptions.NetworkHandler

open class BaseRepository {

    suspend fun <T> handleRemote(callApi: suspend () -> BaseResponse<T>): RemoteResult<T> {
        return try {
            val res = callApi()
            if (res.isSuccessful()) {
                RemoteResult.Success(res.data)
            } else {
                RemoteResult.Error(res.errorMsg, res.errorCode)
            }
        } catch (e: Exception) {
            val exception = NetworkHandler.handleException(e)
            RemoteResult.Error(exception.errMsg, exception.errCode)
        }
    }

}