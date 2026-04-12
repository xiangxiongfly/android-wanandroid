package com.example.mywanandroid.common.utils

import android.app.Application
import com.hjq.toast.Toaster

object ToastUtils {

    fun init(app: Application) {
        Toaster.init(app)
    }

    fun showToast(msg: String) {
        Toaster.show(msg)
    }

    fun showLongToast(msg: String) {
        Toaster.showLong(msg)
    }
}

fun showToast(msg: String) {
    ToastUtils.showToast(msg)
}

fun showLongToast(msg: String) {
    ToastUtils.showLongToast(msg)
}