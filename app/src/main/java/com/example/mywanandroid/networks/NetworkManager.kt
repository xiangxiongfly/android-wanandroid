package com.example.mywanandroid.networks

import com.example.mywanandroid.networks.NetworkConfig.CONNECT_TIMEOUT
import com.example.mywanandroid.networks.NetworkConfig.READ_TIMEOUT
import com.example.mywanandroid.networks.NetworkConfig.WRITE_TIMEOUT
import com.example.mywanandroid.networks.interceptor.CookieInterceptor
import com.example.mywanandroid.networks.interceptor.HttpLogInterceptor
import com.example.mywanandroid.networks.interceptor.UrlInterceptor
import com.hjq.gson.factory.GsonFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkManager {

    val defaultOkHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(UrlInterceptor())
            .addInterceptor(HttpLogInterceptor())
            .addInterceptor(CookieInterceptor())
            .build()
    }

    val defaultRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonFactory.getSingletonGson()))
            .client(defaultOkHttpClient)
            .build()
    }

    private val serviceCache = mutableMapOf<String, Any>()

    fun <T> getService(serviceClass: Class<T>, retrofit: Retrofit = defaultRetrofit): T {
        return serviceCache.getOrPut(serviceClass.name) {
            retrofit.create(serviceClass) as Any
        } as T
    }
}