package com.example.mywanandroid.networks.interceptor

import android.util.Log
import com.example.mywanandroid.data.respository.local.CookieDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class CookieInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val cookieSet = CookieDataStore.getInstance().getCookieSet()
        Log.e("TAG","cookieSet= ${cookieSet}")
        cookieSet?.let {
            for (cookie in it) {
                builder.addHeader("Cookie", cookie)
            }
        }
        val request = builder.build()
        val originalResponse: Response = chain.proceed(request)
        val cookies = originalResponse.headers("Set-Cookie")
        if (cookies.isNotEmpty()) {
            val cookieSet = mutableSetOf<String>()
            for (cookie in cookies) {
                cookieSet.add(cookie)
            }
            runBlocking { CookieDataStore.getInstance().saveCookieSet(cookieSet) }

            val cookieSet2 = CookieDataStore.getInstance().getCookieSet()
            Log.e("TAG","cookieSet2= ${cookieSet2}")
        }
        return originalResponse
    }
}