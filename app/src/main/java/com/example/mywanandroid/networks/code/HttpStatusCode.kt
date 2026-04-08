package com.example.mywanandroid.networks.code

/**
 * Http状态码
 */
object HttpStatusCode {
    const val CONTINUE = 100 // 继续。客户端应继续其请求
    const val SWITCHING_PROTOCOLS = 101 // 切换协议。服务器根据客户端的请求切换协议。

    const val OK = 200 // 成功。
    const val CREATED = 201 // 已创建。请求成功并且服务器创建了新的资源。
    const val ACCEPTED = 202 // 已接受。服务器已接受请求，但尚未处理。
    const val NO_CONTENT = 204 // 无内容。服务器成功处理了请求，但没有返回任何内容。

    const val MOVED_PERMANENTLY = 301 // 永久移动。资源已被永久移动到新位置。
    const val FOUND = 302 // 临时移动。资源临时移动到另一个URI。
    const val NOT_MODIFIED = 304 // 未修改。自从上次请求后资源未被修改，可使用缓存的版本。

    const val BAD_REQUEST = 400 // 错误请求。服务器无法理解请求。
    const val UNAUTHORIZED = 401 // 未授权。请求需要用户的身份验证。
    const val FORBIDDEN = 403 // 禁止访问。服务器理解请求但拒绝执行。
    const val NOT_FOUND = 404 // 未找到。服务器找不到请求的资源。
    const val METHOD_NOT_ALLOWED = 405 // 方法不允许。请求方法不被允许。
    const val REQUEST_TIMEOUT = 408 // 请求超时。服务器等待请求时超时。

    const val INTERNAL_SERVER_ERROR = 500 // 内部服务器错误。服务器遇到错误，无法完成请求。
    const val NOT_IMPLEMENTED = 501 // 未实现。服务器不支持请求的功能。
    const val BAD_GATEWAY = 502 // 错误网关。服务器作为网关或代理，从上游服务器接收到无效响应。
    const val SERVICE_UNAVAILABLE = 503 // 服务不可用。服务器目前无法使用（由于超载或停机维护）。
    const val GATEWAY_TIMEOUT = 504 // 网关超时。服务器作为网关或代理，但是没有及时从上游服务器收到请求。
}