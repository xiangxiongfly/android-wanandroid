package com.example.mywanandroid.ui.tree.details

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.mywanandroid.base.BaseFragment
import com.example.mywanandroid.common.widgets.decoration.LinearDividerDecoration
import com.example.mywanandroid.data.state.LOAD_TYPE_LOAD_MORE
import com.example.mywanandroid.data.state.LOAD_TYPE_REFRESH
import com.example.mywanandroid.data.state.ListUiState
import com.example.mywanandroid.databinding.FragmentTreeArticleListBinding
import com.example.mywanandroid.ui.home.ArticleAdapter

class TreeArticleListFragment : BaseFragment<FragmentTreeArticleListBinding>(FragmentTreeArticleListBinding::inflate) {
    private val viewModel: TreeArticleListViewModel by viewModels { TreeArticleListViewModel.Factory(id) }
    private lateinit var adapter: ArticleAdapter

    private var id = -1

    override fun initArguments(arguments: Bundle) {
        super.initArguments(arguments)
        id = arguments.getInt("id", -1)
    }

    override fun initViews() {
        adapter = ArticleAdapter()
        binding.rvArticle.addItemDecoration(LinearDividerDecoration())
        binding.rvArticle.adapter = adapter
        binding.refreshLayout.setOnRefreshListener { viewModel.refreshArticleList() }
        binding.refreshLayout.setOnLoadMoreListener { viewModel.loadMoreArticleList() }
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewModel.refreshArticleList()
        setupObserves()
    }

    private fun setupObserves() {
        launch {
            viewModel.articleListState.collect {
                when (it) {
                    is ListUiState.Success -> {
                        if (it.loadType == LOAD_TYPE_REFRESH) {
                            binding.refreshLayout.finishRefresh()
                            adapter.submitList(it.items)
                        } else if (it.loadType == LOAD_TYPE_LOAD_MORE) {
                            binding.refreshLayout.finishLoadMore()
                            adapter.addAll(it.items)
                        }
                    }

                    is ListUiState.Error -> {
                        if (binding.refreshLayout.isRefreshing) {
                            binding.refreshLayout.finishRefresh()
                        } else if (binding.refreshLayout.isLoading) {
                            binding.refreshLayout.finishLoadMore()
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    companion object {
        fun newInstance(id: Int) =
            TreeArticleListFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", id)
                }
            }
    }
}