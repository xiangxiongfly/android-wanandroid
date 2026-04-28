package com.example.mywanandroid.domain.repository

import com.example.mywanandroid.data.model.Nav
import com.example.mywanandroid.data.state.Resource
import kotlinx.coroutines.flow.Flow

interface NavRepository {

    /**
     * 获取导航
     */
    fun getNav(): Flow<Resource<List<Nav>>>

}