package com.example.mywanandroid.data.respository.remote.api

import com.example.mywanandroid.data.model.BaseResponse
import com.example.mywanandroid.data.model.Nav
import retrofit2.http.GET

interface NavApi {
    @GET("navi/json")
    suspend fun getNav(): BaseResponse<List<Nav>>
}