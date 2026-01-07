package edu.only4.danmuku.adapter.portal.api.payload.user_message

/**
 * 删除消息接口载荷
 */
object DeleteMessage {

    data class Request(
        val messageId: Long
    )

    class Response
}
