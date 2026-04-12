package com.example.mywanandroid.data.respository.remote.api

import com.example.mywanandroid.data.model.ArticleList
import com.example.mywanandroid.data.model.BaseResponse
import com.example.mywanandroid.data.model.Tree
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TreeApi {

    @GET("tree/json")
    suspend fun getTreeList(): BaseResponse<List<Tree>>

    @GET("article/list/{page}/json")
    suspend fun getTreeArticleList(@Path("page") page: Int, @Query("cid") cid: Int): BaseResponse<ArticleList>
}