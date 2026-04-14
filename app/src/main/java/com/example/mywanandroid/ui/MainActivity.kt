package com.example.mywanandroid.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mywanandroid.R
import com.example.mywanandroid.base.BaseActivity
import com.example.mywanandroid.common.dialog.LogoutDialog
import com.example.mywanandroid.common.utils.showToast
import com.example.mywanandroid.data.respository.local.CookieDataStore
import com.example.mywanandroid.data.respository.local.UserDataStore
import com.example.mywanandroid.databinding.ActivityMainBinding
import com.example.mywanandroid.databinding.DrawerHeaderBinding
import com.example.mywanandroid.ui.articles.ArticlesActivity
import com.example.mywanandroid.ui.chapter.ChapterFragment
import com.example.mywanandroid.ui.home.HomeFragment
import com.example.mywanandroid.ui.info.InfoActivity
import com.example.mywanandroid.ui.login.LoginActivity
import com.example.mywanandroid.ui.nav.NavFragment
import com.example.mywanandroid.ui.project.ProjectFragment
import com.example.mywanandroid.ui.tree.TreeFragment
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var headerBinding: DrawerHeaderBinding
    private val titles = listOf("首页", "项目", "体系", "导航", "公众号")
    private val fragments = listOf(
        HomeFragment.newInstance(),
        ProjectFragment.newInstance(),
        TreeFragment.newInstance(),
        NavFragment.newInstance(),
        ChapterFragment.newInstance(),
    )

    override fun initViews() {
        binding.content.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar?) {
                super.onLeftClick(titleBar)
                binding.drawerLayout.open()
            }
        })
        binding.content.viewPager.isUserInputEnabled = false
        val pageAdapter = PageAdapter(this, fragments)
        binding.content.viewPager.adapter = pageAdapter
        binding.content.bottomNav.setOnItemSelectedListener {
            val position = getPositionFromMenuId(it.itemId)
            binding.content.titleBar.title = titles[position]
            binding.content.viewPager.setCurrentItem(position, false);
            return@setOnItemSelectedListener true
        }
        binding.content.titleBar.title = titles[0]

        binding.leftNav.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.collect -> {
                    if (!UserDataStore.getInstance().isLogin()) {
                        showToast("请先登录")
                    } else {
                        ArticlesActivity.actionStart(context, "收藏的文章", -1)
                    }
                }

                R.id.info -> {
                    InfoActivity.actionStart(context)
                }

                R.id.exit -> {
                    if (UserDataStore.getInstance().isLogin()) {
                        LogoutDialog(context).setOnConfirmListener {
                            launch {
                                headerBinding.tvNickname.text = "请登录"
                                UserDataStore.getInstance().clear()
                                CookieDataStore.getInstance().clear()
                            }
                            binding.drawerLayout.close()
                        }.show()
                    }
                }
            }
            return@setNavigationItemSelectedListener true
        }
        val headerView = binding.leftNav.getHeaderView(0)
        headerBinding = DrawerHeaderBinding.bind(headerView)
        headerBinding.tvNickname.setOnClickListener {
            if (!UserDataStore.getInstance().isLogin()) {
                LoginActivity.actionStart(this@MainActivity)
            }
        }
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
        if (UserDataStore.getInstance().isLogin()) {
            val user = UserDataStore.getInstance().getUser()
            headerBinding.tvNickname.text = user?.username
        } else {
            headerBinding.tvNickname.text = null
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val username = data.getStringExtra("username")
            headerBinding.tvNickname.text = username
        }
    }

    class PageAdapter(activity: FragmentActivity, private val fragments: List<Fragment>) :
        FragmentStateAdapter(activity) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
}