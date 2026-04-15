package com.example.mywanandroid.ui.tree.articles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mywanandroid.base.BaseActivity
import com.example.mywanandroid.data.model.TreeChild
import com.example.mywanandroid.databinding.ActivityTreeArticlesBinding
import com.example.mywanandroid.ui.articles.ArticlesActivity.Companion.TYPE_ARTICLE
import com.example.mywanandroid.ui.articles.ArticlesFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

class TreeArticlesActivity : BaseActivity<ActivityTreeArticlesBinding>(ActivityTreeArticlesBinding::inflate) {

    private var title = ""
    private var children = mutableListOf<TreeChild>()
    private val fragments = mutableListOf<ArticlesFragment>()
    private val titles = mutableListOf<String>()

    companion object {
        fun actionStart(context: Context, title: String, children: ArrayList<TreeChild>) {
            context.startActivity(Intent(context, TreeArticlesActivity::class.java).apply {
                putExtra("children", children)
                putExtra("title", title)
            })
        }
    }

    override fun initExtras(intent: Intent) {
        super.initExtras(intent)
        title = intent.getStringExtra("title") ?: ""
        val list = intent.getSerializableExtra("children") as? ArrayList<TreeChild>
        list?.let {
            children.addAll(list)
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
        for (child in children) {
            titles.add(child.name)
            fragments.add(ArticlesFragment.newInstance(TYPE_ARTICLE, child.id))
        }
        binding.viewPager.adapter = PageAdapter(this, fragments)
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager
        ) { tab, position ->
            tab.setText(titles[position])
        }.attach()
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    class PageAdapter(activity: FragmentActivity, val fragments: List<Fragment>) :
        FragmentStateAdapter(activity) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
}