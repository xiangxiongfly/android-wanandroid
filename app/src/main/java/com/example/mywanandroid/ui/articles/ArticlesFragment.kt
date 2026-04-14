package com.example.mywanandroid.ui.articles

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mywanandroid.R
import com.example.mywanandroid.base.BaseFragment
import com.example.mywanandroid.common.utils.showToast
import com.example.mywanandroid.common.widgets.decoration.LinearDividerDecoration
import com.example.mywanandroid.data.state.LOAD_TYPE_LOAD_MORE
import com.example.mywanandroid.data.state.LOAD_TYPE_REFRESH
import com.example.mywanandroid.data.state.ListUiState
import com.example.mywanandroid.data.state.UiState
import com.example.mywanandroid.databinding.FragmentArticlesBinding
import com.example.mywanandroid.ui.home.ArticleAdapter
import com.example.mywanandroid.ui.webview.WebViewActivity

class ArticlesFragment : BaseFragment<FragmentArticlesBinding>(FragmentArticlesBinding::inflate) {

    private val viewModel: ArticlesViewModel by viewModels { ArticlesViewModel.Factory(id) }
    private lateinit var adapter: ArticleAdapter
    private var id = -1
    private var collectPosition = -1
    private var uncollectPosition = -1

    companion object {
        fun newInstance(id: Int) =
            ArticlesFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", id)
                }
            }
    }

    override fun initArguments(arguments: Bundle) {
        super.initArguments(arguments)
        id = arguments.getInt("id", -1)
    }

    override fun initViews() {
        adapter = ArticleAdapter().apply {
            setCollectType(id == -1)
            setOnItemClickListener { adapter, view, i ->
                val item = items[i]
                WebViewActivity.actionStart(context, item.title, item.link)
            }
            addOnItemChildClickListener(R.id.iv_collect, { adapter, view, position ->
                val item = items[position]
                if (id == -1) {
                    uncollectPosition = position
                    viewModel.uncollectArticleWithCollection(viewLifecycleOwner.lifecycleScope, item.id, item.originId)
                } else {
                    if (item.collect) {
                        uncollectPosition = position
                        viewModel.uncollectArticle(viewLifecycleOwner.lifecycleScope, item.id)
                    } else {
                        collectPosition = position
                        viewModel.collectArticle(viewLifecycleOwner.lifecycleScope, item.id)
                    }
                }
            })
        }
        binding.rvArticle.addItemDecoration(LinearDividerDecoration())
        binding.rvArticle.adapter = adapter
        binding.refreshLayout.setOnLoadMoreListener { viewModel.loadMoreArticles(viewLifecycleOwner.lifecycleScope) }
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewModel.refreshArticles(viewLifecycleOwner.lifecycleScope)
        setupObserves()
    }

    private fun setupObserves() {
        launch {
            viewModel.state.collect {
                when (it) {
                    is ListUiState.Refreshing -> {
                        showLoading()
                    }

                    is ListUiState.Success -> {
                        if (it.loadType == LOAD_TYPE_REFRESH) {
                            hideLoading()
                            adapter.submitList(it.items)
                        } else if (it.loadType == LOAD_TYPE_LOAD_MORE) {
                            binding.refreshLayout.finishLoadMore()
                            adapter.addAll(it.items)
                        }
                    }

                    is ListUiState.Error -> {
                        if (it.loadType == LOAD_TYPE_REFRESH) {
                            hideLoading()
                        } else if (it.loadType == LOAD_TYPE_LOAD_MORE) {
                            binding.refreshLayout.finishLoadMore()
                        }
                        showToast(it.errMsg)
                    }

                    else -> {}
                }
            }
        }
        launch {
            viewModel.collectState.collect {
                when (it) {
                    is UiState.Loading -> {
                        showLoading()
                    }

                    is UiState.Empty -> {
                        hideLoading()
                        showToast("收藏成功")
                        if (collectPosition != -1) {
                            adapter.items[collectPosition].collect = true
                            adapter.notifyItemChanged(collectPosition)
                        }
                        collectPosition = -1
                    }

                    is UiState.Error -> {
                        hideLoading()
                        showToast("收藏失败")
                    }

                    else -> {}
                }
            }
        }
        launch {
            viewModel.uncollectState.collect {
                when (it) {
                    is UiState.Loading -> {
                        showLoading()
                    }

                    is UiState.Empty -> {
                        hideLoading()
                        showToast("取消成功")
                        if (uncollectPosition != -1) {
                            adapter.items[uncollectPosition].collect = false
                            adapter.notifyItemChanged(uncollectPosition)
                        }
                        uncollectPosition = -1
                    }

                    is UiState.Error -> {
                        hideLoading()
                        showToast("取消失败")
                    }

                    else -> {}
                }
            }
        }
        launch {
            viewModel.uncollectWithCollectionState.collect {
                when (it) {
                    is UiState.Loading -> {
                        showLoading()
                    }

                    is UiState.Empty -> {
                        hideLoading()
                        showToast("取消成功")
                        if (uncollectPosition != -1) {
                            adapter.removeAt(uncollectPosition)
                        }
                        uncollectPosition = -1
                    }

                    is UiState.Error -> {
                        hideLoading()
                        showToast("取消失败")
                    }

                    else -> {}
                }
            }
        }
    }
}