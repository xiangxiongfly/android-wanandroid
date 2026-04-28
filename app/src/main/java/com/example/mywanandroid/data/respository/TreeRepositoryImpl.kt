package com.example.mywanandroid.data.respository

import com.example.mywanandroid.base.BaseRepository
import com.example.mywanandroid.data.respository.remote.api.TreeApi
import com.example.mywanandroid.data.state.RemoteResult
import com.example.mywanandroid.data.state.Resource
import com.example.mywanandroid.domain.repository.TreeRepository
import com.example.mywanandroid.networks.NetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TreeRepositoryImpl(val treeApi: TreeApi = NetworkManager.getService(TreeApi::class.java)) :
    BaseRepository(), TreeRepository {

    override fun getTreeList() = flow {
        when (val result = handleRemote { treeApi.getTreeList() }) {
            is RemoteResult.Success -> {
                emit(Resource.Success(result.data))
            }

            is RemoteResult.Error -> {
                emit(Resource.Error(result.errMsg, result.errCode))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getTreeArticleList(page: Int, cid: Int) = flow {
        when (val result = handleRemote { treeApi.getTreeArticleList(page, cid) }) {
            is RemoteResult.Success -> {
                emit(Resource.Success(result.data))
            }

            is RemoteResult.Error -> {
                emit(Resource.Error(result.errMsg, result.errCode))
            }
        }
    }.flowOn(Dispatchers.IO)

}