package com.example.mywanandroid.ui.home

import com.example.mywanandroid.base.BaseViewModel
import com.example.mywanandroid.data.model.Banner
import com.example.mywanandroid.data.respository.HomeRepo
import com.example.mywanandroid.data.state.Resource
import com.example.mywanandroid.data.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : BaseViewModel() {
    private val repo by lazy { HomeRepo() }
    private val _bannerState = MutableStateFlow<UiState<List<Banner>>>(UiState.Idle)
    val bannerState = _bannerState.asStateFlow()

    fun getBanner() {
        launchIO {
            _bannerState.value = UiState.Loading
            repo.getBanner().collect {
                when (it) {
                    is Resource.Success -> {
                        _bannerState.value = UiState.Success(it.data)
                    }

                    is Resource.Error -> {
                        _bannerState.value = UiState.Error(it.errMsg, it.errCode)
                    }
                }
            }
        }
    }

}