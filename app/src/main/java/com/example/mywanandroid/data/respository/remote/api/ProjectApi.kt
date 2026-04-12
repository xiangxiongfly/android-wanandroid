package com.example.mywanandroid.data.respository.remote.api

import com.example.mywanandroid.data.model.BaseResponse
import com.example.mywanandroid.data.model.ProjectArticleList
import com.example.mywanandroid.data.model.ProjectTree
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectApi {
    @GET("project/tree/json")
    suspend fun getProjectTree(): BaseResponse<List<ProjectTree>>

    @GET("project/list/{page}/json")
    suspend fun getProjectArticleList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): BaseResponse<ProjectArticleList>
}