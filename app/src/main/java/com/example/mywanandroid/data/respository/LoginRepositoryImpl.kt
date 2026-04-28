package com.example.mywanandroid.data.respository

import com.example.mywanandroid.base.BaseRepository
import com.example.mywanandroid.data.respository.local.UserDataStore
import com.example.mywanandroid.data.respository.remote.api.LoginApi
import com.example.mywanandroid.data.state.RemoteResult
import com.example.mywanandroid.data.state.Resource
import com.example.mywanandroid.domain.repository.LoginRepository
import com.example.mywanandroid.networks.NetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRepositoryImpl(val loginApi: LoginApi = NetworkManager.getService(LoginApi::class.java)) : BaseRepository(),
    LoginRepository {

    override fun login(username: String, password: String) = flow {
        when (val result = handleRemote { loginApi.login(username, password) }) {
            is RemoteResult.Success -> {
                emit(Resource.Success(result.data))
                UserDataStore.getInstance().saveUser(result.data)
            }

            is RemoteResult.Error -> {
                emit(Resource.Error(result.errMsg, result.errCode))
            }
        }
    }.flowOn(Dispatchers.IO)
}