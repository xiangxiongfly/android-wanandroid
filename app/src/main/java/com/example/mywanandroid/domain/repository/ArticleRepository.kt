package com.example.mywanandroid.domain.repository

import com.example.mywanandroid.data.model.ArticleList
import com.example.mywanandroid.data.state.Resource
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    /**
     * 获取公众号文章
     */
    fun getChapterArticles(id: Int, page: Int): Flow<Resource<ArticleList>>

    /**
     * 获取已收藏文章
     */
    fun getCollectArticles(page: Int): Flow<Resource<ArticleList>>

    /**
     * 获取搜索文章
     */
    fun getQueryArticles(page: Int, k: String): Flow<Resource<ArticleList>>

    /**
     * 收藏文章
     */
    fun collectArticle(id: Int): Flow<Resource<Any>>

    /**
     * 取消收藏
     */
    fun uncollectArticle(id: Int): Flow<Resource<Any>>

    /**
     * 取消收藏
     */
    fun uncollectArticleWithCollection(id: Int, originId: Int): Flow<Resource<Any>>

}