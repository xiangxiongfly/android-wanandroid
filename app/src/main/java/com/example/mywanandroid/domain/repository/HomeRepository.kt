package com.example.mywanandroid.domain.repository

import com.example.mywanandroid.data.model.ArticleList
import com.example.mywanandroid.data.model.Banner
import com.example.mywanandroid.data.state.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    /**
     * 获取轮播图
     */
    fun getBanner(): Flow<Resource<List<Banner>>>

    /**
     * 获取首页文章
     */
    fun getArticleList(page: Int): Flow<Resource<ArticleList>>
}