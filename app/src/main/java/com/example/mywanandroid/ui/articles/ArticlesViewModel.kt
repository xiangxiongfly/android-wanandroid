package com.example.mywanandroid.ui.articles

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mywanandroid.base.BaseViewModel
import com.example.mywanandroid.data.model.Article
import com.example.mywanandroid.data.respository.ArticleRepository
import com.example.mywanandroid.data.state.LOAD_TYPE_LOAD_MORE
import com.example.mywanandroid.data.state.LOAD_TYPE_REFRESH
import com.example.mywanandroid.data.state.ListUiState
import com.example.mywanandroid.data.state.Resource
import com.example.mywanandroid.data.state.UiState
import com.example.mywanandroid.ui.articles.ArticlesActivity.Companion.TYPE_COLLECTION
import com.example.mywanandroid.ui.articles.ArticlesActivity.Companion.TYPE_QUERY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ArticlesViewModel(private val type: Int) : BaseViewModel() {
    private val repo by lazy { ArticleRepository() }
    private val _state = MutableStateFlow<ListUiState<Article>>(ListUiState.Idle)
    val state = _state.asStateFlow()
    private val _collectState = MutableStateFlow<UiState<Any>>(UiState.Idle)
    val collectState = _collectState.asStateFlow()
    private val _uncollectState = MutableStateFlow<UiState<Any>>(UiState.Idle)
    val uncollectState = _uncollectState.asStateFlow()
    private val _uncollectWithCollectionState = MutableStateFlow<UiState<Any>>(UiState.Idle)
    val uncollectWithCollectionState = _uncollectWithCollectionState.asStateFlow()

    private var page = FIRST_PAGE
    private var id = -1
    private var keyword = ""

    companion object {
        const val FIRST_PAGE = 0
    }

    class Factory(private val type: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ArticlesViewModel(type) as T
        }
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun setKeyword(keyword: String) {
        this.keyword = keyword
    }

    fun refreshArticles(lifecycleScope: LifecycleCoroutineScope) {
        launchIO(lifecycleScope) {
            _state.value = ListUiState.Refreshing
            page = FIRST_PAGE

            (when (type) {
                TYPE_COLLECTION -> {
                    repo.getCollectArticles(page)
                }

                TYPE_QUERY -> {
                    repo.getQueryArticles(page, keyword)
                }

                else -> {
                    repo.getChapterArticles(id, page)
                }
            }).collect {
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

    fun loadMoreArticles(lifecycleScope: LifecycleCoroutineScope) {
        launchIO(lifecycleScope) {
            _state.value = ListUiState.LoadingMore
            (when (type) {
                TYPE_COLLECTION -> {
                    repo.getCollectArticles(page)
                }

                TYPE_QUERY -> {
                    repo.getQueryArticles(page, keyword)
                }

                else -> {
                    repo.getChapterArticles(id, page)
                }
            }).collect {
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

    fun collectArticle(lifecycleScope: LifecycleCoroutineScope, id: Int) {
        launchIO(lifecycleScope) {
            _collectState.value = UiState.Loading
            repo.collectArticle(id).collect {
                when (it) {
                    is Resource.Success -> {
                        _collectState.value = UiState.Empty
                    }

                    is Resource.Error -> {
                        _collectState.value = UiState.Error(it.errMsg)
                    }
                }
            }
        }
    }

    fun uncollectArticle(lifecycleScope: LifecycleCoroutineScope, id: Int) {
        launchIO(lifecycleScope) {
            _uncollectState.value = UiState.Loading
            repo.uncollectArticle(id).collect {
                when (it) {
                    is Resource.Success -> {
                        _uncollectState.value = UiState.Empty
                    }

                    is Resource.Error -> {
                        _uncollectState.value = UiState.Error(it.errMsg)
                    }
                }
            }
        }
    }

    fun uncollectArticleWithCollection(lifecycleScope: LifecycleCoroutineScope, id: Int, originId: Int) {
        launchIO(lifecycleScope) {
            _uncollectWithCollectionState.value = UiState.Loading
            repo.uncollectArticleWithCollection(id, originId).collect {
                when (it) {
                    is Resource.Success -> {
                        _uncollectWithCollectionState.value = UiState.Empty
                    }

                    is Resource.Error -> {
                        _uncollectWithCollectionState.value = UiState.Error(it.errMsg)
                    }
                }
            }
        }
    }
}