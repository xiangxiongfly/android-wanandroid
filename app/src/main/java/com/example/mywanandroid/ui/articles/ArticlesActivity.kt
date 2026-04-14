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
    private var title = ""
    private var id = -1

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
        title = intent.getStringExtra("title") ?: ""
        id = intent.getIntExtra("id", -1)
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
            .replace(R.id.fragment_container, ArticlesFragment.newInstance(id))
            .commit()
    }
}