package com.example.mywanandroid.data.respository.remote.api

import com.example.mywanandroid.data.model.Banner
import com.example.mywanandroid.data.model.BaseResponse
import retrofit2.http.GET

interface HomeApi {

    @GET("banner/json")
    suspend fun getBanner(): BaseResponse<List<Banner>>


}