package com.example.mywanandroid.data.respository.remote.api

import com.example.mywanandroid.data.model.ArticleList
import com.example.mywanandroid.data.model.BaseResponse
import com.example.mywanandroid.data.model.Chapter
import retrofit2.http.GET
import retrofit2.http.Path

interface ChapterApi {

    @GET("wxarticle/chapters/json")
    suspend fun getChapterList(): BaseResponse<List<Chapter>>

    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getChapterArticles(@Path("id") id: Int, @Path("page") page: Int): BaseResponse<ArticleList>
}