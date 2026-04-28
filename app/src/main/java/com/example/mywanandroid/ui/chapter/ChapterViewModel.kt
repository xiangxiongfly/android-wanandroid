package com.example.mywanandroid.ui.chapter

import com.example.mywanandroid.base.BaseViewModel
import com.example.mywanandroid.data.model.Chapter
import com.example.mywanandroid.data.respository.ChapterRepositoryImpl
import com.example.mywanandroid.data.state.Resource
import com.example.mywanandroid.data.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChapterViewModel : BaseViewModel() {
    private val repo = ChapterRepositoryImpl()
    private val _state = MutableStateFlow<UiState<List<Chapter>>>(UiState.Idle)
    val state = _state.asStateFlow()

    init {
        getChapterList()
    }

    fun getChapterList() {
        launchIO {
            _state.value = UiState.Loading
            repo.getChapterList().collect {
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