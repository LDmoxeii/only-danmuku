package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 获取未读消息数接口载荷
 */
object MessageGetNoReadCount {

    class Request

    data class Response(
        /** 未读消息数 */
        var count: Int? = null
    )
}
