package com.example.mywanandroid.common.utils

import com.hjq.gson.factory.GsonFactory

object GsonUtils {

    fun toJson(obj: Any) = GsonFactory.getSingletonGson().toJson(obj)
}