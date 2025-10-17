package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 标记全部已读接口载荷
 */
object MessageReadAll {

    data class Request(
        /** 消息类型 */
        val messageType: Int? = null
    )

    class Response
}
