package com.example.mywanandroid.networks.exceptions

import android.content.Context
import android.net.ConnectivityManager
import android.net.ParseException
import com.example.mywanandroid.base.BaseApp
import com.example.mywanandroid.networks.code.ErrorCode
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

object NetworkHandler {
    fun handleException(e: Throwable): RuleException {
        return when (e) {
            is HttpException -> {
                RuleException(ErrorCode.HTTP_ERROR, "服务器连接失败${e.code()}")
            }

            is JsonParseException, is JSONException, is ParseException -> {
                RuleException(ErrorCode.PARSE_ERROR, "解析错误")
            }

            is ConnectException, is MalformedJsonException -> {
                RuleException(ErrorCode.NETWORD_ERROR, "网络连接失败,请稍后重试")
            }

            is SocketTimeoutException, is java.net.SocketException, is ConnectTimeoutException -> {
                RuleException(ErrorCode.TIMEOUT_ERROR, "连接超时")
            }

            is SSLHandshakeException -> {
                RuleException(ErrorCode.SSL_ERROR, "证书验证失败")
            }

            is UnknownHostException -> {
                val connectivityManager =
                    BaseApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    RuleException(ErrorCode.NETWORD_ERROR, "网络连接失败,请稍后重试")
                } else {
                    RuleException(ErrorCode.NOT_NETWORK_ERROR, "当前无网络,请检查网络设置")
                }
            }

            else -> {
                RuleException(ErrorCode.UNKNOWN, "网络连接异常,请稍后重试")
            }
        }
    }
}