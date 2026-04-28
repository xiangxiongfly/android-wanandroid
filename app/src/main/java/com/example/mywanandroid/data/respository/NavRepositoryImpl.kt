package com.example.mywanandroid.data.respository

import com.example.mywanandroid.base.BaseRepository
import com.example.mywanandroid.data.respository.remote.api.NavApi
import com.example.mywanandroid.data.state.RemoteResult
import com.example.mywanandroid.data.state.Resource
import com.example.mywanandroid.domain.repository.NavRepository
import com.example.mywanandroid.networks.NetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NavRepositoryImpl(val navApi: NavApi = NetworkManager.getService(NavApi::class.java)) : BaseRepository(),
    NavRepository {

    override fun getNav() = flow {
        when (val result = handleRemote { navApi.getNav() }) {
            is RemoteResult.Success -> {
                emit(Resource.Success(result.data))
            }

            is RemoteResult.Error -> {
                emit(Resource.Error(result.errMsg, result.errCode))
            }
        }
    }.flowOn(Dispatchers.IO)

}