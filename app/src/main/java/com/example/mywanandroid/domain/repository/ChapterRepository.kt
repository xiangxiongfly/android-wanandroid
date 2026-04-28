package com.example.mywanandroid.domain.repository

import com.example.mywanandroid.data.model.Chapter
import com.example.mywanandroid.data.state.Resource
import kotlinx.coroutines.flow.Flow

interface ChapterRepository {

    /**
     * 获取公众号
     */
    fun getChapterList(): Flow<Resource<List<Chapter>>>
}