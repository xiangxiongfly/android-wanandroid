package com.example.mywanandroid.data.respository.remote.api

import com.example.mywanandroid.data.model.BaseResponse
import com.example.mywanandroid.data.model.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") username: String, @Field("password") password: String): BaseResponse<User>
}