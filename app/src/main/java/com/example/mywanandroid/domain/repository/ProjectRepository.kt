package com.example.mywanandroid.domain.repository

import com.example.mywanandroid.data.model.ProjectArticleList
import com.example.mywanandroid.data.model.ProjectTree
import com.example.mywanandroid.data.state.Resource
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {

    /**
     * 获取项目页签
     */
    fun getProjectTree(): Flow<Resource<List<ProjectTree>>>

    /**
     * 获取项目文章
     */
    fun getProjectArticleList(page: Int, cid: Int): Flow<Resource<ProjectArticleList>>
}