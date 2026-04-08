package com.example.mywanandroid.networks.code

/**
 * 异常状态码
 */
object ErrorCode {
    /**
     * 未知错误
     */
    const val UNKNOWN = -9000

    /**
     * 解析错误
     */
    const val PARSE_ERROR = -9001

    /**
     * 网络错误
     */
    const val NETWORD_ERROR = -9002

    /**
     * 协议出错
     */
    const val HTTP_ERROR = -9003

    /**
     * 证书出错
     */
    const val SSL_ERROR = -9005

    /**
     * 连接超时
     */
    const val TIMEOUT_ERROR = -9006

    /**
     * 无网
     */
    const val NOT_NETWORK_ERROR = -9007
}