package com.example.mywanandroid.ui.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.mywanandroid.base.BaseFragment
import com.example.mywanandroid.common.widgets.decoration.LinearDividerDecoration
import com.example.mywanandroid.data.state.LOAD_TYPE_INITIAL
import com.example.mywanandroid.data.state.LOAD_TYPE_LOAD_MORE
import com.example.mywanandroid.data.state.LOAD_TYPE_REFRESH
import com.example.mywanandroid.data.state.ListUiState
import com.example.mywanandroid.data.state.UiState
import com.example.mywanandroid.databinding.FragmentHomeBinding
import com.example.mywanandroid.ui.home.banner.BannerAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: ArticleAdapter

    override fun initViews() {
        setupViewPager()
        setupList()
    }

    private fun setupViewPager() {
        binding.bannerView.apply {
            setAdapter(BannerAdapter())
            registerLifecycleObserver(lifecycle)
        }.create()
    }

    private fun setupList() {
        adapter = ArticleAdapter()
        binding.rvHome.addItemDecoration(LinearDividerDecoration())
        binding.rvHome.adapter = adapter
        binding.refreshLayout.setOnRefreshListener { viewModel.refreshArticles() }
        binding.refreshLayout.setOnLoadMoreListener { viewModel.loadMoreArticles() }
    }

    override fun initData(savedInstanceState: Bundle?) {
        setupObserves()
    }

    private fun setupObserves() {
        launch {
            viewModel.bannerState.collect {
                when (it) {
                    is UiState.Success -> {
                        binding.bannerView.refreshData(it.data)
                    }

                    else -> {
                    }
                }
            }
        }
        launch {
            viewModel.articlesState.collect {
                when (it) {
                    is ListUiState.Loading -> {
                        showLoading()
                    }

                    is ListUiState.Success -> {
                        hideLoading()
                        if (it.loadType == LOAD_TYPE_INITIAL) {
                            adapter.submitList(it.items)
                        } else if (it.loadType == LOAD_TYPE_REFRESH) {
                            adapter.submitList(it.items)
                            binding.refreshLayout.finishRefresh()
                        } else if (it.loadType == LOAD_TYPE_LOAD_MORE) {
                            adapter.addAll(it.items)
                            binding.refreshLayout.finishLoadMore()
                        }
                    }

                    is ListUiState.Error -> {
                        hideLoading()
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
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}