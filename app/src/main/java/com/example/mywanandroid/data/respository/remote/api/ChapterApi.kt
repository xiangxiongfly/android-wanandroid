package com.example.mywanandroid.data.respository.remote.api

import com.example.mywanandroid.data.model.BaseResponse
import retrofit2.http.GET

interface ChapterApi {

    @GET("wxarticle/chapters/json")
    suspend fun getBanner(): BaseResponse<List<ChapterApi>>
}