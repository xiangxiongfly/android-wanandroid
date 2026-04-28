package com.example.mywanandroid.domain.repository

import com.example.mywanandroid.data.model.Hotkey
import com.example.mywanandroid.data.state.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    /**
     * 获取热词
     */
    fun getHotkey(): Flow<Resource<List<Hotkey>>>
}