package com.example.mywanandroid.networks

import com.example.mywanandroid.networks.NetworkConfig.CONNECT_TIMEOUT
import com.example.mywanandroid.networks.NetworkConfig.READ_TIMEOUT
import com.example.mywanandroid.networks.NetworkConfig.WRITE_TIMEOUT
import com.example.mywanandroid.networks.interceptor.HttpLogInterceptor
import com.hjq.gson.factory.GsonFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkManager {

    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLogInterceptor())
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonFactory.getSingletonGson()))
            .client(okHttpClient)
            .build()
    }

    fun <T> create(retrofit: Retrofit, serviceClass: Class<T>): T = retrofit.create(serviceClass)
}