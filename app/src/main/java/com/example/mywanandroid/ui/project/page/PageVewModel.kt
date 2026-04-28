package com.example.mywanandroid.ui.project.page

import androidx.lifecycle.LifecycleCoroutineScope
import com.example.mywanandroid.base.BaseViewModel
import com.example.mywanandroid.data.model.ProjectArticle
import com.example.mywanandroid.data.respository.ProjectRepositoryImpl
import com.example.mywanandroid.data.state.LOAD_TYPE_LOAD_MORE
import com.example.mywanandroid.data.state.LOAD_TYPE_REFRESH
import com.example.mywanandroid.data.state.ListUiState
import com.example.mywanandroid.data.state.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PageVewModel : BaseViewModel() {
    private val repo = ProjectRepositoryImpl()
    private val _articlesState =
        MutableStateFlow<ListUiState<ProjectArticle>>(ListUiState.Idle)
    val articlesState = _articlesState.asStateFlow()

    private var page = FIRST_PAGE
    private var cid = -1

    companion object {
        const val FIRST_PAGE = 0
    }

    fun setId(id: Int) {
        cid = id
    }

    fun refreshProjectArticles(coroutineScope: LifecycleCoroutineScope) {
        launchIO(coroutineScope) {
            _articlesState.value = ListUiState.Refreshing
            page = FIRST_PAGE
            repo.getProjectArticleList(page, cid).collect { it ->
                when (it) {
                    is Resource.Success -> {
                        val list = it.data.datas.map {
                            it.envelopePic = it.envelopePic.replace(
                                "www.wanandroid.com",
                                "wanandroid.com"
                            )
                            it
                        }
                        _articlesState.value = ListUiState.Success(
                            LOAD_TYPE_REFRESH,
                            list
                        )
                        page++
                    }

                    is Resource.Error -> {
                        _articlesState.value =
                            ListUiState.Error(LOAD_TYPE_REFRESH, it.errMsg, it.errCode)
                    }
                }
            }
        }
    }

    fun loadMoreProjectArticles(coroutineScope: LifecycleCoroutineScope) {
        launch(coroutineScope) {
            _articlesState.value = ListUiState.LoadingMore
            repo.getProjectArticleList(page, cid).collect {
                when (it) {
                    is Resource.Success -> {
                        val list = it.data.datas.map {
                            it.envelopePic = it.envelopePic.replace(
                                "www.wanandroid.com",
                                "wanandroid.com"
                            )
                            it
                        }
                        _articlesState.value = ListUiState.Success(
                            LOAD_TYPE_LOAD_MORE,
                            list
                        )
                        page++
                    }

                    is Resource.Error -> {
                        _articlesState.value =
                            ListUiState.Error(LOAD_TYPE_LOAD_MORE, it.errMsg, it.errCode)
                    }
                }
            }
        }
    }
}