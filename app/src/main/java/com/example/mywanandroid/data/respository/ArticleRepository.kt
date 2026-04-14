package com.example.mywanandroid.data.respository

import com.example.mywanandroid.base.BaseRepository
import com.example.mywanandroid.data.respository.local.UserDataStore
import com.example.mywanandroid.data.respository.remote.api.ArticleApi
import com.example.mywanandroid.data.state.RemoteResult
import com.example.mywanandroid.data.state.Resource
import com.example.mywanandroid.networks.NetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ArticleRepository(private val articleApi: ArticleApi = NetworkManager.create(ArticleApi::class.java)) :
    BaseRepository() {

    // 公众号文章
    fun getChapterArticles(id: Int, page: Int) = flow {
        when (val result = handleRemote { articleApi.getChapterArticles(id, page) }) {
            is RemoteResult.Success -> {
                emit(Resource.Success(result.data))
            }

            is RemoteResult.Error -> {
                emit(Resource.Error(result.errMsg, result.errCode))
            }
        }
    }.flowOn(Dispatchers.IO)

    // 已收藏文章
    fun getCollectArticles(page: Int) = flow {
        when (val result = handleRemote { articleApi.getCollectArticles(page) }) {
            is RemoteResult.Success -> {
                emit(Resource.Success(result.data))
            }

            is RemoteResult.Error -> {
                emit(Resource.Error(result.errMsg, result.errCode))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun collectArticle(id: Int) = flow {
        if (UserDataStore.getInstance().isLogin()) {
            when (val result = handleRemote { articleApi.collectArticle(id) }) {
                is RemoteResult.Success -> {
                    emit(Resource.Success(result.data))
                }

                is RemoteResult.Error -> {
                    emit(Resource.Error(result.errMsg, result.errCode))
                }
            }
        } else {
            emit(Resource.Error("请先登录"))
        }
    }.flowOn(Dispatchers.IO)

    fun uncollectArticle(id: Int) = flow {
        when (val result = handleRemote { articleApi.uncollectArticle(id) }) {
            is RemoteResult.Success -> {
                emit(Resource.Success(result.data))
            }

            is RemoteResult.Error -> {
                emit(Resource.Error(result.errMsg, result.errCode))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun uncollectArticleWithCollection(id: Int, originId: Int) = flow {
        when (val result = handleRemote { articleApi.uncollectArticleWithCollection(id, originId) }) {
            is RemoteResult.Success -> {
                emit(Resource.Success(result.data))
            }

            is RemoteResult.Error -> {
                emit(Resource.Error(result.errMsg, result.errCode))
            }
        }
    }.flowOn(Dispatchers.IO)

}