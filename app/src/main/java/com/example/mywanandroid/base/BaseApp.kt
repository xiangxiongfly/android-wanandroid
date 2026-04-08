package com.example.mywanandroid.base

import android.app.Application

class BaseApp : Application() {
    companion object {
        private var instance: BaseApp? = null

        fun getInstance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}