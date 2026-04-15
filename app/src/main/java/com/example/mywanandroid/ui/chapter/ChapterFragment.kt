package com.example.mywanandroid.ui.chapter

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.mywanandroid.base.BaseFragment
import com.example.mywanandroid.common.utils.ToastUtils.showToast
import com.example.mywanandroid.data.state.UiState
import com.example.mywanandroid.databinding.FragmentChapterBinding
import com.example.mywanandroid.ui.articles.ArticlesActivity
import com.example.mywanandroid.ui.articles.ArticlesActivity.Companion.TYPE_ARTICLE


class ChapterFragment : BaseFragment<FragmentChapterBinding>(FragmentChapterBinding::inflate) {
    private val viewModel: ChapterViewModel by viewModels()
    private lateinit var adapter: ChapterAdapter

    companion object {
        @JvmStatic
        fun newInstance() = ChapterFragment()
    }

    override fun initViews() {
        adapter = ChapterAdapter().apply {
            setOnItemClickListener { _, _, i ->
                val item = items[i]
                ArticlesActivity.actionStart(context, TYPE_ARTICLE, item.name, item.id)
            }
        }
        binding.rvChapter.adapter = adapter
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
                        adapter.submitList(it.data)
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