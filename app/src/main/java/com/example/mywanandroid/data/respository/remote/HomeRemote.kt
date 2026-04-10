package com.example.mywanandroid.data.respository.remote

import com.example.mywanandroid.data.model.ArticleList
import com.example.mywanandroid.data.model.Banner
import com.example.mywanandroid.data.respository.remote.api.HomeApi
import com.example.mywanandroid.data.state.RemoteResult
import com.example.mywanandroid.networks.NetworkManager
import com.example.mywanandroid.networks.exceptions.NetworkHandler

class HomeRemote(private val homeApi: HomeApi = NetworkManager.create(HomeApi::class.java)) {
    suspend fun getBanner(): RemoteResult<List<Banner>> {
        return try {
            val res = homeApi.getBanner()
            if (res.isSuccessful()) {
                RemoteResult.Success(res.data)
            } else {
                RemoteResult.Error(res.errorMsg, res.errorCode)
            }
        } catch (e: Exception) {
            val exception = NetworkHandler.handleException(e)
            RemoteResult.Error(exception.errMsg, exception.errCode)
        }
    }

    suspend fun getArticleList(page:Int): RemoteResult<ArticleList> {
        return try {
            val res = homeApi.getArticleList(page)
            if (res.isSuccessful()) {
                RemoteResult.Success(res.data)
            } else {
                RemoteResult.Error(res.errorMsg, res.errorCode)
            }
        } catch (e: Exception) {
            val exception = NetworkHandler.handleException(e)
            RemoteResult.Error(exception.errMsg, exception.errCode)
        }
    }
}