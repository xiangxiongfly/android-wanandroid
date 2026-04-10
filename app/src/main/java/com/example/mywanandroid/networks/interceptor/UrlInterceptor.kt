package com.example.mywanandroid.networks.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class UrlInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        var newUrl = originalRequest.url.toString()

        if (newUrl.contains("www.wanandroid.com")) {
            newUrl = newUrl.replace("www.wanandroid.com", "wanandroid.com")
        }

        val newRequest = if (newUrl != originalRequest.url.toString()) {
            originalRequest.newBuilder()
                .url(newUrl)
                .build()
        } else {
            originalRequest
        }

        return chain.proceed(newRequest)
    }
}