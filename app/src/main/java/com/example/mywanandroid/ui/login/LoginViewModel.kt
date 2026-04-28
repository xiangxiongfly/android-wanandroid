package com.example.mywanandroid.ui.login

import com.example.mywanandroid.base.BaseViewModel
import com.example.mywanandroid.data.model.User
import com.example.mywanandroid.data.respository.LoginRepositoryImpl
import com.example.mywanandroid.data.state.Resource
import com.example.mywanandroid.data.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : BaseViewModel() {
    private val repo = LoginRepositoryImpl()
    private val _state = MutableStateFlow<UiState<User>>(UiState.Idle)
    val state = _state.asStateFlow()

    fun login(username: String, password: String) {
        launchIO {
            _state.value = UiState.Loading
            repo.login(username, password).collect {
                when (it) {
                    is Resource.Success -> {
                        _state.value = UiState.Success(it.data)
                    }

                    is Resource.Error -> {
                        _state.value = UiState.Error(it.errMsg, it.errCode)
                    }
                }
            }
        }
    }
}