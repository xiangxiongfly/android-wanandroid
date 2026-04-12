package com.example.mywanandroid.ui.tree.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mywanandroid.base.BaseActivity
import com.example.mywanandroid.data.model.TreeChild
import com.example.mywanandroid.databinding.ActivityTreeArticleListBinding
import com.google.android.material.tabs.TabLayoutMediator

class TreeArticleListActivity : BaseActivity<ActivityTreeArticleListBinding>(ActivityTreeArticleListBinding::inflate) {

    private var children = mutableListOf<TreeChild>()
    private val fragments = mutableListOf<TreeArticleListFragment>()
    private val titles = mutableListOf<String>()

    companion object {
        fun actionStart(context: Context, children: ArrayList<TreeChild>) {
            context.startActivity(Intent(context, TreeArticleListActivity::class.java).apply {
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
            fragments.add(TreeArticleListFragment.newInstance(child.id))
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