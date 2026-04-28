package com.example.mywanandroid.data.respository

import com.example.mywanandroid.base.BaseRepository
import com.example.mywanandroid.data.respository.local.UserDataStore
import com.example.mywanandroid.data.respository.remote.api.ArticleApi
import com.example.mywanandroid.data.state.RemoteResult
import com.example.mywanandroid.data.state.Resource
import com.example.mywanandroid.domain.repository.ArticleRepository
import com.example.mywanandroid.networks.NetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ArticleRepositoryImpl(private val articleApi: ArticleApi = NetworkManager.getService(ArticleApi::class.java)) :
    BaseRepository(), ArticleRepository {

    override fun getChapterArticles(id: Int, page: Int) = flow {
        when (val result = handleRemote { articleApi.getChapterArticles(id, page) }) {
            is RemoteResult.Success -> {
                emit(Resource.Success(result.data))
            }

            is RemoteResult.Error -> {
                emit(Resource.Error(result.errMsg, result.errCode))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getCollectArticles(page: Int) = flow {
        when (val result = handleRemote { articleApi.getCollectArticles(page) }) {
            is RemoteResult.Success -> {
                emit(Resource.Success(result.data))
            }

            is RemoteResult.Error -> {
                emit(Resource.Error(result.errMsg, result.errCode))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getQueryArticles(page: Int, k: String) = flow {
        when (val result = handleRemote { articleApi.queryArticles(page, k) }) {
            is RemoteResult.Success -> {
                emit(Resource.Success(result.data))
            }

            is RemoteResult.Error -> {
                emit(Resource.Error(result.errMsg, result.errCode))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun collectArticle(id: Int) = flow {
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

    override fun uncollectArticle(id: Int) = flow {
        when (val result = handleRemote { articleApi.uncollectArticle(id) }) {
            is RemoteResult.Success -> {
                emit(Resource.Success(result.data))
            }

            is RemoteResult.Error -> {
                emit(Resource.Error(result.errMsg, result.errCode))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun uncollectArticleWithCollection(id: Int, originId: Int) = flow {
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