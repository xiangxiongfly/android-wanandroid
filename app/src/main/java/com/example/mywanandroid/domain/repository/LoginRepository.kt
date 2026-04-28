package com.example.mywanandroid.domain.repository

import com.example.mywanandroid.data.model.User
import com.example.mywanandroid.data.state.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    /**
     * 登录
     */
    fun login(username: String, password: String): Flow<Resource<User>>
}