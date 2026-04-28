package com.example.mywanandroid.ui.search

import com.example.mywanandroid.base.BaseViewModel
import com.example.mywanandroid.data.model.Hotkey
import com.example.mywanandroid.data.respository.SearchRepositoryImpl
import com.example.mywanandroid.data.state.Resource
import com.example.mywanandroid.data.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel : BaseViewModel() {
    private val repo = SearchRepositoryImpl()
    private val _state = MutableStateFlow<UiState<List<Hotkey>>>(UiState.Idle)
    val state = _state.asStateFlow()

    fun getHotkey() {
        launchIO {
            _state.value = UiState.Loading
            repo.getHotkey().collect {
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