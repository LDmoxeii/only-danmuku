package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 删除消息接口载荷
 */
object MessageDel {

    data class Request(
        val messageId: Long
    )

    class Response
}
