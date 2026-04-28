package com.example.mywanandroid.ui.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import com.example.mywanandroid.base.BaseActivity
import com.example.mywanandroid.databinding.ActivityWebViewBinding
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.just.agentweb.AgentWeb

class WebViewActivity : BaseActivity<ActivityWebViewBinding>(ActivityWebViewBinding::inflate) {
    private var title = ""
    private var url = ""
    private var agentWeb: AgentWeb? = null

    companion object {
        fun actionStart(context: Context, title: String, url: String) {
            context.startActivity(Intent(context, WebViewActivity::class.java).apply {
                putExtra("title", title)
                putExtra("url", url)
            })
        }
    }

    override fun initExtras(intent: Intent) {
        super.initExtras(intent)
        title = intent.getStringExtra("title") ?: ""
        url = intent.getStringExtra("url") ?: ""
        url = url.replace("www.wanandroid.com", "wanandroid.com")
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
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.container, ViewGroup.LayoutParams(-1, -1))
            .useDefaultIndicator() // 使用默认进度条
            .createAgentWeb()
            .ready()
            .go(url) // 加载网页
    }

    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        agentWeb?.destroy()
        agentWeb = null
        super.onDestroy()
    }
}