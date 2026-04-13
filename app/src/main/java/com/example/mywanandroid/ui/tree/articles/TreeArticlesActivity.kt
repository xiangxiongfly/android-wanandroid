package com.example.mywanandroid.ui.tree.articles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mywanandroid.base.BaseActivity
import com.example.mywanandroid.data.model.TreeChild
import com.example.mywanandroid.databinding.ActivityTreeArticlesBinding
import com.google.android.material.tabs.TabLayoutMediator

class TreeArticlesActivity : BaseActivity<ActivityTreeArticlesBinding>(ActivityTreeArticlesBinding::inflate) {

    private var children = mutableListOf<TreeChild>()
    private val fragments = mutableListOf<TreeArticlesFragment>()
    private val titles = mutableListOf<String>()

    companion object {
        fun actionStart(context: Context, children: ArrayList<TreeChild>) {
            context.startActivity(Intent(context, TreeArticlesActivity::class.java).apply {
                putExtra("children", children)
            })
        }
    }

    override fun initExtras(intent: Intent) {
        super.initExtras(intent)
        val list = intent.getSerializableExtra("children") as? ArrayList<TreeChild>
        list?.let {
            children.addAll(list)
        }
    }

    override fun initViews() {
        Log.e("TAG", "children= ${children}")
        for (child in children) {
            titles.add(child.name)
            fragments.add(TreeArticlesFragment.newInstance(child.id))
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