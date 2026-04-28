package com.example.mywanandroid.ui.nav

import com.example.mywanandroid.base.BaseViewModel
import com.example.mywanandroid.data.model.Nav
import com.example.mywanandroid.data.respository.NavRepositoryImpl
import com.example.mywanandroid.data.state.Resource
import com.example.mywanandroid.data.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavViewModel : BaseViewModel() {
    private val repo = NavRepositoryImpl()
    private val _state = MutableStateFlow<UiState<List<Nav>>>(UiState.Idle)
    val state = _state.asStateFlow()

    init {
        getNav()
    }

    fun getNav() {
        launchIO {
            _state.value = UiState.Loading
            repo.getNav().collect {
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