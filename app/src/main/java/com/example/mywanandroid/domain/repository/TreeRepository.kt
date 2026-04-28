package com.example.mywanandroid.domain.repository

import com.example.mywanandroid.data.model.ArticleList
import com.example.mywanandroid.data.model.Tree
import com.example.mywanandroid.data.state.Resource
import kotlinx.coroutines.flow.Flow

interface TreeRepository {

    /**
     * 获取体系
     */
    fun getTreeList(): Flow<Resource<List<Tree>>>

    /**
     * 获取体系文章
     */
    fun getTreeArticleList(page: Int, cid: Int): Flow<Resource<ArticleList>>
}