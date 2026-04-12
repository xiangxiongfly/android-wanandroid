package com.example.mywanandroid.ui.nav

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.mywanandroid.base.BaseFragment
import com.example.mywanandroid.common.utils.showToast
import com.example.mywanandroid.data.model.NavArticle
import com.example.mywanandroid.data.state.UiState
import com.example.mywanandroid.databinding.FragmentNavBinding
import com.example.mywanandroid.databinding.ItemNavTagBinding

class NavFragment : BaseFragment<FragmentNavBinding>(FragmentNavBinding::inflate) {
    private val viewModel: NavViewModel by viewModels()
    private lateinit var navTitleAdapter: NavTitleAdapter
    private var selectedPosition = -1

    companion object {
        @JvmStatic
        fun newInstance() = NavFragment()
    }

    override fun initViews() {
        navTitleAdapter = NavTitleAdapter().apply {
            setOnItemClickListener { adapter, view, i ->
                selectedTab(i)
            }
        }
        binding.rvNavTitle.adapter = navTitleAdapter
    }

    private fun selectedTab(position: Int) {
        if (selectedPosition == position) return
        if (selectedPosition != -1) {
            navTitleAdapter.items[selectedPosition].selected = false
            navTitleAdapter.notifyItemChanged(selectedPosition)
        }
        selectedPosition = position
        navTitleAdapter.items[selectedPosition].selected = true
        navTitleAdapter.notifyItemChanged(selectedPosition)
        handleTags(navTitleAdapter.items[position].articles)
    }

    private fun handleTags(articles: List<NavArticle>) {
        binding.fblTag.removeAllViews()
        for (article in articles) {
            val tag = ItemNavTagBinding.inflate(layoutInflater, binding.fblTag, false)
            tag.root.text = article.title
            binding.fblTag.addView(tag.root)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        setupObserves()
    }

    private fun setupObserves() {
        launch {
            viewModel.state.collect {
                when (it) {
                    is UiState.Loading -> showLoading()

                    is UiState.Success -> {
                        hideLoading()
                        navTitleAdapter.submitList(it.data)
                        if (it.data.isNotEmpty()) {
                            selectedTab(0)
                        }
                    }

                    is UiState.Error -> {
                        hideLoading()
                        showToast(it.errMsg)
                    }

                    else -> {}
                }
            }
        }
    }
}