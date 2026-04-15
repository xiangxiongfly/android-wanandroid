package com.example.mywanandroid.base

import android.app.Application
import com.example.mywanandroid.common.utils.MMKVUtils
import com.example.mywanandroid.common.utils.ToastUtils
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

class BaseApp : Application() {
    companion object {
        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                ClassicsHeader(context)
            }
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                ClassicsFooter(context)
            }
        }

        private var instance: BaseApp? = null

        fun getInstance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ToastUtils.init(this)
        MMKVUtils.init(this)
    }
}