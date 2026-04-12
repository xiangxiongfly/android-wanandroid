package com.example.mywanandroid.ui.tree

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.mywanandroid.base.BaseFragment
import com.example.mywanandroid.common.ext.dp
import com.example.mywanandroid.common.utils.showToast
import com.example.mywanandroid.common.widgets.decoration.LinearDividerDecoration
import com.example.mywanandroid.data.model.TreeChild
import com.example.mywanandroid.data.state.ListUiState
import com.example.mywanandroid.databinding.FragmentTreeBinding
import com.example.mywanandroid.ui.tree.details.TreeArticleListActivity

class TreeFragment : BaseFragment<FragmentTreeBinding>(FragmentTreeBinding::inflate) {
    private val viewModel: TreeViewModel by viewModels()
    private lateinit var adapter: TreeAdapter

    companion object {
        @JvmStatic
        fun newInstance() = TreeFragment()
    }

    override fun initViews() {
        adapter = TreeAdapter().apply {
            setOnItemClickListener { adapter, view, i ->
                TreeArticleListActivity.actionStart(context, items[i].children as ArrayList<TreeChild>)
            }
        }
        binding.rvTree.addItemDecoration(LinearDividerDecoration(dividerSize = 2F.dp))
        binding.rvTree.adapter = adapter
    }

    override fun initData(savedInstanceState: Bundle?) {
        setupObserves()
    }

    private fun setupObserves() {
        launch {
            viewModel.treeListState.collect {
                when (it) {
                    is ListUiState.Loading -> showLoading()

                    is ListUiState.Success -> {
                        hideLoading()
                        adapter.submitList(it.items)
                    }

                    is ListUiState.Error -> {
                        hideLoading()
                        showToast(it.errMsg)
                    }

                    else -> {}
                }
            }
        }
    }
}