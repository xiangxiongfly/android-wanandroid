package com.example.mywanandroid.networks.interceptor

import com.example.mywanandroid.utils.LogUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.util.concurrent.TimeUnit

class HttpLogInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        logRequest(request)
        val response: Response
        val startNs = System.nanoTime()
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            log("HTTP FAILED: $e")
            throw e
        }
        val elapsedTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        logResponse(response, elapsedTime)
        return response
    }

    private fun logRequest(request: Request) {
        val requestBody = request.body
        val headers = request.headers
        val builder = StringBuilder()
        builder.append("--> ${request.method} ${request.url}").append("\n")
        if (requestBody != null) {
            if (headers["Content-Type"] == null && requestBody.contentType() != null) {
                builder.append("Content-Type: ${requestBody.contentType()}").append("\n")
            }
            if (headers["Content-Length"] == null && requestBody.contentLength() != -1L) {
                builder.append("Content-Length: ${requestBody.contentLength()}").append("\n")
            }
        }
        builder.append("$headers")
        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            val body = buffer.readUtf8()
            builder.append(body).append("\n")
            builder.append("--> END ${request.method} (${requestBody.contentLength()}-byte body)")
        } else {
            builder.append("--> END ${request.method}")
        }
        log(builder.toString())
    }

    private fun logResponse(response: Response, elapsedTime: Long) {
        val builder = StringBuilder()
        val headers = response.headers
        builder.append("<-- ${response.code} ${response.message} ${response.request.url} (${elapsedTime}ms)")
            .append("\n")
        builder.append("$headers")
        val responseBody = response.body
        if (responseBody != null) {
            val source = responseBody.source()
            source.request(Long.MAX_VALUE)
            val buffer = source.buffer
            val body = buffer.clone().readUtf8()
            builder.append(body).append("\n")
            builder.append("<-- END HTTP (${buffer.size}-byte body)")
        } else {
            builder.append("<-- END HTTP")
        }
        log(builder.toString())
    }

    private fun log(msg: String) {
        LogUtils.e("HTTP", msg)
    }
}