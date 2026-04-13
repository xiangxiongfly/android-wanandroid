package com.example.mywanandroid.ui.articles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mywanandroid.base.BaseActivity
import com.example.mywanandroid.common.utils.showToast
import com.example.mywanandroid.common.widgets.decoration.LinearDividerDecoration
import com.example.mywanandroid.data.state.LOAD_TYPE_LOAD_MORE
import com.example.mywanandroid.data.state.LOAD_TYPE_REFRESH
import com.example.mywanandroid.data.state.ListUiState
import com.example.mywanandroid.databinding.ActivityArticlesBinding
import com.example.mywanandroid.ui.home.ArticleAdapter
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

class ArticlesActivity : BaseActivity<ActivityArticlesBinding>(ActivityArticlesBinding::inflate) {
    private val viewModel: ArticlesViewModel by viewModels { ArticlesViewModel.Factory(id) }
    private lateinit var adapter: ArticleAdapter
    private var id = -1
    private var title = ""

    companion object {
        fun actionStart(context: Context, title: String, id: Int) {
            context.startActivity(
                Intent(context, ArticlesActivity::class.java).apply {
                    putExtra("id", id)
                    putExtra("title", title)
                }
            )
        }
    }

    override fun initExtras(intent: Intent) {
        super.initExtras(intent)
        id = intent.getIntExtra("id", -1)
        title = intent.getStringExtra("title") ?: ""
    }

    override fun initViews() {
        binding.titleBar.title = title
        binding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar?) {
                super.onLeftClick(titleBar)
                finish()
            }
        })
        adapter = ArticleAdapter()
        binding.rvArticle.addItemDecoration(LinearDividerDecoration())
        binding.rvArticle.adapter = adapter
        binding.refreshLayout.setOnLoadMoreListener { viewModel.loadMoreChapterArticles() }
    }

    override fun initData(savedInstanceState: Bundle?) {
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
    }
}