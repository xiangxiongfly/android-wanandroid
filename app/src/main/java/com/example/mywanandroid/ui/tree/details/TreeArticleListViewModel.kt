package com.example.mywanandroid.ui.tree.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mywanandroid.base.BaseViewModel
import com.example.mywanandroid.data.model.Article
import com.example.mywanandroid.data.respository.TreeRepository
import com.example.mywanandroid.data.state.LOAD_TYPE_LOAD_MORE
import com.example.mywanandroid.data.state.LOAD_TYPE_REFRESH
import com.example.mywanandroid.data.state.ListUiState
import com.example.mywanandroid.data.state.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TreeArticleListViewModel(private val id: Int) : BaseViewModel() {
    private val repo by lazy { TreeRepository() }
    private val _articleListState = MutableStateFlow<ListUiState<Article>>(ListUiState.Idle)
    val articleListState = _articleListState.asStateFlow()
    private var page = FIRST_PAGE

    companion object {
        const val FIRST_PAGE = 0
    }

    class Factory(private val id: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TreeArticleListViewModel::class.java)) {
                return TreeArticleListViewModel(id) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun refreshArticleList() {
        launchIO {
            _articleListState.value = ListUiState.Refreshing
            page = FIRST_PAGE
            repo.getTreeArticleList(page, id).collect {
                when (it) {
                    is Resource.Success -> {
                        _articleListState.value = ListUiState.Success(LOAD_TYPE_REFRESH, it.data.datas)
                        page++
                    }

                    is Resource.Error -> {
                        _articleListState.value = ListUiState.Error(LOAD_TYPE_REFRESH, it.errMsg, it.errCode)
                    }
                }
            }
        }
    }

    fun loadMoreArticleList() {
        launchIO {
            _articleListState.value = ListUiState.Refreshing
            repo.getTreeArticleList(page, id).collect {
                when (it) {
                    is Resource.Success -> {
                        _articleListState.value = ListUiState.Success(LOAD_TYPE_LOAD_MORE, it.data.datas)
                        page++
                    }

                    is Resource.Error -> {
                        _articleListState.value = ListUiState.Error(LOAD_TYPE_LOAD_MORE, it.errMsg, it.errCode)
                    }
                }
            }
        }
    }

}