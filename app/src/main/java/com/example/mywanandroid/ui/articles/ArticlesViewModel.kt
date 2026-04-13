package com.example.mywanandroid.ui.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mywanandroid.base.BaseViewModel
import com.example.mywanandroid.data.model.Article
import com.example.mywanandroid.data.respository.ChapterRepository
import com.example.mywanandroid.data.state.LOAD_TYPE_LOAD_MORE
import com.example.mywanandroid.data.state.LOAD_TYPE_REFRESH
import com.example.mywanandroid.data.state.ListUiState
import com.example.mywanandroid.data.state.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ArticlesViewModel(private val id: Int) : BaseViewModel() {
    private val repo by lazy { ChapterRepository() }
    private val _state = MutableStateFlow<ListUiState<Article>>(ListUiState.Idle)
    val state = _state.asStateFlow()

    private var page = FIRST_PAGE

    companion object {
        const val FIRST_PAGE = 0
    }

    class Factory(private val id: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ArticlesViewModel(id) as T
        }
    }

    init {
        refreshChapterArticles()
    }

    fun refreshChapterArticles() {
        launchIO {
            _state.value = ListUiState.Refreshing
            page = FIRST_PAGE
            repo.getChapterArticles(id, page).collect {
                when (it) {
                    is Resource.Success -> {
                        _state.value = ListUiState.Success(LOAD_TYPE_REFRESH, it.data.datas)
                        page++
                    }

                    is Resource.Error -> {
                        _state.value = ListUiState.Error(LOAD_TYPE_REFRESH, it.errMsg, it.errCode)
                    }
                }
            }
        }
    }

    fun loadMoreChapterArticles() {
        launchIO {
            _state.value = ListUiState.LoadingMore
            repo.getChapterArticles(id, page).collect {
                when (it) {
                    is Resource.Success -> {
                        _state.value = ListUiState.Success(LOAD_TYPE_LOAD_MORE, it.data.datas)
                        page++
                    }

                    is Resource.Error -> {
                        _state.value = ListUiState.Error(LOAD_TYPE_LOAD_MORE, it.errMsg, it.errCode)
                    }
                }
            }
        }
    }
}