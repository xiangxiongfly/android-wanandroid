package com.example.mywanandroid.ui.search

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import com.example.mywanandroid.base.BaseActivity
import com.example.mywanandroid.common.ext.dp
import com.example.mywanandroid.common.utils.MMKVUtils
import com.example.mywanandroid.common.utils.showToast
import com.example.mywanandroid.data.constants.HOTKEYS
import com.example.mywanandroid.data.model.Hotkey
import com.example.mywanandroid.data.state.UiState
import com.example.mywanandroid.databinding.ActivitySearchBinding
import com.example.mywanandroid.ui.articles.ArticlesActivity
import com.example.mywanandroid.ui.articles.ArticlesActivity.Companion.TYPE_QUERY
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.hjq.shape.view.ShapeTextView

class SearchActivity : BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate) {
    private val viewModel: SearchViewModel by viewModels()

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, SearchActivity::class.java))
        }
    }

    override fun initViews() {
        binding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar?) {
                super.onLeftClick(titleBar)
                finish()
            }
        })
        binding.search.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.ivDelete.setOnClickListener {
            MMKVUtils.remove(HOTKEYS)
            binding.fblSearchHistory.removeAllViews()
        }
    }

    private fun doSearch() {
        val keywords = binding.search.text.toString().trim()
        if (TextUtils.isEmpty(keywords)) {
            showToast("请输入关键字")
            return
        }
        ArticlesActivity.actionStart(context, TYPE_QUERY, keywords, keyword = keywords)
        val hotkeySet = MMKVUtils.getStringSet(HOTKEYS)
        val set = hotkeySet.toHashSet()
        set.add(keywords)
        handleSearchHistory(set)
        MMKVUtils.putStringSet(HOTKEYS, set)
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewModel.getHotkey()
        setupObserves()

        val hotkeySet = MMKVUtils.getStringSet(HOTKEYS)
        handleSearchHistory(hotkeySet)
    }

    private fun setupObserves() {
        launch {
            viewModel.state.collect {
                when (it) {
                    is UiState.Success -> {
                        val list = it.data
                        handleHotkey(list)
                    }

                    is UiState.Error -> {
                        showToast(it.errMsg)
                    }

                    else -> {}
                }
            }
        }
    }

    private fun handleSearchHistory(set: Set<String>) {
        binding.fblSearchHistory.removeAllViews()
        for (str in set) {
            val textView = TextView(context).apply {
                text = str
                textSize = 14F
                setTextColor(Color.parseColor("#ff0000"))
                setOnClickListener {
                    val keyword = (it as TextView).text.toString()
                    ArticlesActivity.actionStart(context, TYPE_QUERY, keyword, keyword = keyword)
                }
            }
            binding.fblSearchHistory.addView(textView)
        }
    }

    private fun handleHotkey(list: List<Hotkey>) {
        binding.fblHotkey.removeAllViews()
        for (item in list) {
            val textView = ShapeTextView(context).apply {
                text = item.name
                textSize = 14F
                setTextColor(Color.parseColor("#ff1989fa"))
                setPadding(8.dp, 4.dp, 8.dp, 4.dp)
                shapeDrawableBuilder.apply {
                    strokeColor = Color.parseColor("#ff1989fa")
                    strokeSize = 1.dp
                    setRadius(4F.dp)
                    intoBackground()
                }
                setOnClickListener {
                    val keyword = (it as TextView).text.toString()
                    ArticlesActivity.actionStart(context, TYPE_QUERY, keyword, keyword = keyword)
                }
            }
            binding.fblHotkey.addView(textView)
        }
    }
}