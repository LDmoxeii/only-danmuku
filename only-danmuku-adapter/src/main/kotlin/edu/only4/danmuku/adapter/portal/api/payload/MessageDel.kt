package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 删除消息接口载荷
 */
object MessageDel {

    data class Request(
        /** 消息ID */

        @field:NotEmpty(message = "消息ID不能为空")
        val messageId: Int = 0
    )

    class Response
}
