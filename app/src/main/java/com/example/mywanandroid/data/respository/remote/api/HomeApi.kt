package com.example.mywanandroid.data.respository.remote.api

import com.example.mywanandroid.data.model.ArticleList
import com.example.mywanandroid.data.model.Banner
import com.example.mywanandroid.data.model.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeApi {

    @GET("banner/json")
    suspend fun getBanner(): BaseResponse<List<Banner>>

    @GET("article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): BaseResponse<ArticleList>

}