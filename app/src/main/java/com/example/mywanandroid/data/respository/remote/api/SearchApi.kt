package com.example.mywanandroid.data.respository.remote.api

import com.example.mywanandroid.data.model.BaseResponse
import com.example.mywanandroid.data.model.Hotkey
import retrofit2.http.GET

interface SearchApi {

    @GET("hotkey/json")
    suspend fun getHotkey(): BaseResponse<List<Hotkey>>
}