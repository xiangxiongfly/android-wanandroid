package com.example.mywanandroid.ui.project.page

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mywanandroid.base.BaseFragment
import com.example.mywanandroid.common.utils.showToast
import com.example.mywanandroid.common.widgets.decoration.LinearDividerDecoration
import com.example.mywanandroid.data.state.LOAD_TYPE_LOAD_MORE
import com.example.mywanandroid.data.state.LOAD_TYPE_REFRESH
import com.example.mywanandroid.data.state.ListUiState
import com.example.mywanandroid.databinding.FragmentPageBinding

class PageFragment : BaseFragment<FragmentPageBinding>(FragmentPageBinding::inflate) {
    private val viewModel: PageVewModel by viewModels()
    private lateinit var adapter: PageAdapter
    private var name = ""
    private var id = -1

    companion object {
        fun newInstance(id: Int, name: String) =
            PageFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", id)
                    putString("name", name)
                }
            }
    }

    override fun initArguments(arguments: Bundle) {
        super.initArguments(arguments)
        arguments.let {
            name = arguments.getString("name", "")
            id = arguments.getInt("id", -1)
        }
    }

    override fun initViews() {
        adapter = PageAdapter()
        binding.rvArticle.addItemDecoration(LinearDividerDecoration())
        binding.rvArticle.adapter = adapter
        binding.refreshLayout.setOnRefreshListener { viewModel.refreshProjectArticles(lifecycleScope) }
        binding.refreshLayout.setOnLoadMoreListener {
            viewModel.loadMoreProjectArticles(
                lifecycleScope
            )
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewModel.setId(id)
        viewModel.refreshProjectArticles(lifecycleScope)
        setupObserves()
    }

    private fun setupObserves() {
        launch {
            viewModel.articlesState.collect {
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
                        showToast(it.errMsg)
                    }

                    else -> {}
                }
            }
        }
    }

}