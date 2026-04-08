package com.example.mywanandroid.base

import com.example.mywanandroid.data.model.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

open class BaseRepository {

    fun <T> handleRemoteData(request: suspend () -> BaseResponse<T>) = flow {
        val res = request()
        if (res.isSuccessful()) {
            emit("chengg")
        } else {

        }

    }.catch {

    }.flowOn(Dispatchers.IO)

}