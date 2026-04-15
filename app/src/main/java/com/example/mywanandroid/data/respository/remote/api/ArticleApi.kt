package com.example.mywanandroid.data.respository.remote.api

import com.example.mywanandroid.data.model.ArticleList
import com.example.mywanandroid.data.model.BaseResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ArticleApi {

    @FormUrlEncoded
    @POST("article/query/{page}/json")
    suspend fun queryArticles(@Path("page") page: Int, @Field("k") k: String): BaseResponse<ArticleList>

    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getChapterArticles(@Path("id") id: Int, @Path("page") page: Int): BaseResponse<ArticleList>

    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectArticles(@Path("page") page: Int): BaseResponse<ArticleList>

    @POST("lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): BaseResponse<Any>

    @POST("lg/uncollect_originId/{id}/json")
    suspend fun uncollectArticle(@Path("id") id: Int): BaseResponse<Any>

    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    suspend fun uncollectArticleWithCollection(@Path("id") id: Int, @Field("originId") originId: Int): BaseResponse<Any>
}