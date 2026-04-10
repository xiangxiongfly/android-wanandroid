package com.example.mywanandroid.ui.home

import com.example.mywanandroid.base.BaseViewModel
import com.example.mywanandroid.data.model.Article
import com.example.mywanandroid.data.model.Banner
import com.example.mywanandroid.data.respository.HomeRepository
import com.example.mywanandroid.data.state.LOAD_TYPE_INITIAL
import com.example.mywanandroid.data.state.LOAD_TYPE_LOAD_MORE
import com.example.mywanandroid.data.state.LOAD_TYPE_REFRESH
import com.example.mywanandroid.data.state.ListState
import com.example.mywanandroid.data.state.Resource
import com.example.mywanandroid.data.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : BaseViewModel() {
    private val repo by lazy { HomeRepository() }
    private val _bannerState = MutableStateFlow<UiState<List<Banner>>>(UiState.Idle)
    val bannerState = _bannerState.asStateFlow()
    private val _articlesState = MutableStateFlow<ListState<Article>>(ListState.Idle)
    val articlesState = _articlesState.asStateFlow()

    private var page = 0
    private var pageCount = 0
    private val allItems = mutableListOf<Article>()

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

    fun loadArticles() {
        if (_articlesState.value is ListState.Loading ||
            _articlesState.value is ListState.Refreshing ||
            _articlesState.value is ListState.LoadingMore
        ) {
            return
        }
        launchIO {
            _articlesState.value = ListState.Loading
            page = 0
            repo.getArticleList(page).collect {
                when (it) {
                    is Resource.Success -> {
                        val data = it.data
                        val list = data.datas
                        page = data.curPage
                        pageCount = data.pageCount
                        allItems.clear()
                        allItems.addAll(list)
                        _articlesState.value = ListState.Success(LOAD_TYPE_INITIAL, list)
                        page++
                    }

                    is Resource.Error -> {
                        _articlesState.value = ListState.Error(LOAD_TYPE_INITIAL, it.errMsg)
                    }
                }
            }
        }
    }

    fun refreshArticles() {
        if (_articlesState.value is ListState.Loading ||
            _articlesState.value is ListState.Refreshing ||
            _articlesState.value is ListState.LoadingMore
        ) {
            return
        }
        launchIO {
            _articlesState.value = ListState.Refreshing
            page = 0
            repo.getArticleList(page).collect {
                when (it) {
                    is Resource.Success -> {
                        val data = it.data
                        val list = data.datas
                        page = data.curPage
                        pageCount = data.pageCount
                        allItems.clear()
                        allItems.addAll(list)
                        _articlesState.value = ListState.Success(LOAD_TYPE_REFRESH, list)
                        page++
                    }

                    is Resource.Error -> {
                        _articlesState.value = ListState.Error(LOAD_TYPE_REFRESH, it.errMsg)
                    }
                }
            }
        }
    }

    fun loadMoreArticles() {
        if (_articlesState.value is ListState.Loading ||
            _articlesState.value is ListState.Refreshing ||
            _articlesState.value is ListState.LoadingMore
        ) {
            return
        }
        launchIO {
            _articlesState.value = ListState.LoadingMore
            repo.getArticleList(page).collect {
                when (it) {
                    is Resource.Success -> {
                        val data = it.data
                        val list = data.datas
                        page = data.curPage
                        pageCount = data.pageCount
                        allItems.addAll(list)
                        _articlesState.value = ListState.Success(LOAD_TYPE_LOAD_MORE, list)
                        page++
                    }

                    is Resource.Error -> {
                        _articlesState.value = ListState.Error(LOAD_TYPE_LOAD_MORE, it.errMsg)
                    }
                }
            }
        }
    }
}