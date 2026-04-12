package com.example.mywanandroid.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mywanandroid.R
import com.example.mywanandroid.base.BaseActivity
import com.example.mywanandroid.databinding.ActivityMainBinding
import com.example.mywanandroid.ui.chapter.ChapterFragment
import com.example.mywanandroid.ui.home.HomeFragment
import com.example.mywanandroid.ui.nav.NavFragment
import com.example.mywanandroid.ui.project.ProjectFragment
import com.example.mywanandroid.ui.tree.TreeFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private val titles = listOf("首页", "项目", "体系", "导航", "公众号")

    override fun initViews() {
        binding.viewPager.isUserInputEnabled = false
        val pageAdapter = PageAdapter(this)
        binding.viewPager.adapter = pageAdapter
        binding.bottomNav.setOnItemSelectedListener {
            val position = getPositionFromMenuId(it.itemId)
            binding.titleBar.title = titles[position]
            binding.viewPager.setCurrentItem(position, false);
            return@setOnItemSelectedListener true
        }
        binding.titleBar.title = titles[0]
    }

    private fun getPositionFromMenuId(menuId: Int): Int {
        return when (menuId) {
            R.id.home -> 0
            R.id.project -> 1
            R.id.tree -> 2
            R.id.nav -> 3
            R.id.chapter -> 4
            else -> 0
        }
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    class PageAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        private val fragments = listOf(
            HomeFragment.newInstance(),
            ProjectFragment.newInstance(),
            TreeFragment.newInstance(),
            NavFragment.newInstance(),
            ChapterFragment.newInstance(),
        )

        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
}