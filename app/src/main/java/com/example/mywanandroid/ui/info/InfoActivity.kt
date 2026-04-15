package com.example.mywanandroid.ui.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.mywanandroid.base.BaseActivity
import com.example.mywanandroid.databinding.ActivityInfoBinding
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

class InfoActivity : BaseActivity<ActivityInfoBinding>(ActivityInfoBinding::inflate) {

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, InfoActivity::class.java))
        }
    }

    override fun initViews() {
        binding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar?) {
                super.onLeftClick(titleBar)
                finish()
            }
        })
        binding.tvDesc.text = """
        项目名：wanandroid
        语言：Kotlin
        架构：MVVM-Clean
        网络：OkHttp+Retrofit
        图片：Coil
        json解析：Gson
        列表：SmartRefreshLayout
        图形：ShapeView
        提示：Toaster
        轮播图：BannerViewPager
        标题栏：TitleBar
        WebView：AgentWeb      
""".trimIndent()
    }

    override fun initData(savedInstanceState: Bundle?) {

    }
}