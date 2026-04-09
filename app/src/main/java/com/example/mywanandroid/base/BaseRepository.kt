package com.example.mywanandroid.base

import com.example.mywanandroid.data.model.BaseResponse
import com.example.mywanandroid.data.state.Resource
import com.example.mywanandroid.networks.exceptions.NetworkHandler

open class BaseRepository {

    suspend fun <T> handleRemote(apiCall: suspend () -> BaseResponse<T>): Resource<T> {
        return try {
            val res = apiCall()
            if (res.isSuccessful()) {
                Resource.Success(res.data)
            } else {
                Resource.Error(res.errorMsg, res.errorCode)
            }
        } catch (e: Exception) {
            val exception = NetworkHandler.handleException(e)
            Resource.Error(exception.errMsg, exception.errCode)
        }
    }

}