package com.example.mywanandroid.ui.articles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.mywanandroid.R
import com.example.mywanandroid.base.BaseActivity
import com.example.mywanandroid.databinding.ActivityArticlesBinding
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

class ArticlesActivity : BaseActivity<ActivityArticlesBinding>(ActivityArticlesBinding::inflate) {
    private var type = -1
    private var title = ""
    private var id: Int? = null
    private var keyword: String? = null

    companion object {
        const val TYPE_ARTICLE = 1
        const val TYPE_COLLECTION = 2
        const val TYPE_QUERY = 3
        fun actionStart(context: Context, type: Int, title: String, id: Int? = null, keyword: String? = null) {
            context.startActivity(
                Intent(context, ArticlesActivity::class.java).apply {
                    putExtra("type", type)
                    putExtra("title", title)
                    if (id != null) {
                        putExtra("id", id)
                    }
                    if (keyword != null) {
                        putExtra("keyword", keyword)
                    }
                }
            )
        }
    }

    override fun initExtras(intent: Intent) {
        super.initExtras(intent)
        type = intent.getIntExtra("type", -1)
        title = intent.getStringExtra("title") ?: ""
        if (intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1)
        }
        if (intent.hasExtra("keyword")) {
            keyword = intent.getStringExtra("keyword") ?: ""
        }
    }

    override fun initViews() {
        binding.titleBar.title = title
        binding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar?) {
                super.onLeftClick(titleBar)
                finish()
            }
        })
    }

    override fun initData(savedInstanceState: Bundle?) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ArticlesFragment.newInstance(type, id, keyword))
            .commit()
    }
}