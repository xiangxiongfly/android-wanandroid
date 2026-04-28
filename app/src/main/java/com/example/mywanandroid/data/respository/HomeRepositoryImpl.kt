package com.example.mywanandroid.data.respository

import com.example.mywanandroid.base.BaseRepository
import com.example.mywanandroid.data.respository.remote.api.HomeApi
import com.example.mywanandroid.data.state.RemoteResult
import com.example.mywanandroid.data.state.Resource
import com.example.mywanandroid.domain.repository.HomeRepository
import com.example.mywanandroid.networks.NetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HomeRepositoryImpl(private val homeApi: HomeApi = NetworkManager.getService(HomeApi::class.java)) :
    BaseRepository(), HomeRepository {

    override fun getBanner() = flow {
        when (val result = handleRemote { homeApi.getBanner() }) {
            is RemoteResult.Success -> {
                emit(Resource.Success(result.data))
            }

            is RemoteResult.Error -> {
                emit(Resource.Error(result.errMsg, result.errCode))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getArticleList(page: Int) = flow {
        when (val result = handleRemote { homeApi.getArticleList(page) }) {
            is RemoteResult.Success -> {
                emit(Resource.Success(result.data))
            }

            is RemoteResult.Error -> {
                emit(Resource.Error(result.errMsg, result.errCode))
            }
        }
    }.flowOn(Dispatchers.IO)
}