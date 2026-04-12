package com.example.mywanandroid.ui.home

import com.example.mywanandroid.base.BaseViewModel
import com.example.mywanandroid.data.model.Article
import com.example.mywanandroid.data.model.Banner
import com.example.mywanandroid.data.respository.HomeRepository
import com.example.mywanandroid.data.state.LOAD_TYPE_INITIAL
import com.example.mywanandroid.data.state.LOAD_TYPE_LOAD_MORE
import com.example.mywanandroid.data.state.LOAD_TYPE_REFRESH
import com.example.mywanandroid.data.state.ListUiState
import com.example.mywanandroid.data.state.Resource
import com.example.mywanandroid.data.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : BaseViewModel() {

    companion object {
        const val FIRST_PAGE = 0
    }

    private val repo by lazy { HomeRepository() }
    private val _bannerState = MutableStateFlow<UiState<List<Banner>>>(UiState.Idle)
    val bannerState = _bannerState.asStateFlow()
    private val _articlesState = MutableStateFlow<ListUiState<Article>>(ListUiState.Idle)
    val articlesState = _articlesState.asStateFlow()

    private var page = FIRST_PAGE
    private val allItems = mutableListOf<Article>()


    init {
        getBanner()
        loadArticles()
    }

    fun getBanner() {
        launchIO {
            _bannerState.value = UiState.Loading
            repo.getBanner().collect { it ->
                when (it) {
                    is Resource.Success -> {
                        val list = it.data.map {
                            it.imagePath = it.imagePath.replace(
                                "www.wanandroid.com",
                                "wanandroid.com"
                            )
                            it
                        }
                        _bannerState.value = UiState.Success(list)
                    }

                    is Resource.Error -> {
                        _bannerState.value = UiState.Error(it.errMsg, it.errCode)
                    }
                }
            }
        }
    }

    fun loadArticles() {
        if (_articlesState.value is ListUiState.Loading ||
            _articlesState.value is ListUiState.Refreshing ||
            _articlesState.value is ListUiState.LoadingMore
        ) {
            return
        }
        launchIO {
            _articlesState.value = ListUiState.Loading
            page = FIRST_PAGE
            repo.getArticleList(page).collect {
                when (it) {
                    is Resource.Success -> {
                        val data = it.data
                        val list = data.datas
                        allItems.clear()
                        allItems.addAll(list)
                        _articlesState.value = ListUiState.Success(LOAD_TYPE_INITIAL, list)
                        page++
                    }

                    is Resource.Error -> {
                        _articlesState.value = ListUiState.Error(LOAD_TYPE_INITIAL, it.errMsg)
                    }
                }
            }
        }
    }

    fun refreshArticles() {
        if (_articlesState.value is ListUiState.Loading ||
            _articlesState.value is ListUiState.Refreshing ||
            _articlesState.value is ListUiState.LoadingMore
        ) {
            return
        }
        launchIO {
            _articlesState.value = ListUiState.Refreshing
            page = FIRST_PAGE
            repo.getArticleList(page).collect {
                when (it) {
                    is Resource.Success -> {
                        val data = it.data
                        val list = data.datas
                        allItems.clear()
                        allItems.addAll(list)
                        _articlesState.value = ListUiState.Success(LOAD_TYPE_REFRESH, list)
                        page++
                    }

                    is Resource.Error -> {
                        _articlesState.value = ListUiState.Error(LOAD_TYPE_REFRESH, it.errMsg)
                    }
                }
            }
        }
    }

    fun loadMoreArticles() {
        if (_articlesState.value is ListUiState.Loading ||
            _articlesState.value is ListUiState.Refreshing ||
            _articlesState.value is ListUiState.LoadingMore
        ) {
            return
        }
        launchIO {
            _articlesState.value = ListUiState.LoadingMore
            repo.getArticleList(page).collect {
                when (it) {
                    is Resource.Success -> {
                        val data = it.data
                        val list = data.datas
                        allItems.addAll(list)
                        _articlesState.value = ListUiState.Success(LOAD_TYPE_LOAD_MORE, list)
                        page++
                    }

                    is Resource.Error -> {
                        _articlesState.value = ListUiState.Error(LOAD_TYPE_LOAD_MORE, it.errMsg)
                    }
                }
            }
        }
    }
}