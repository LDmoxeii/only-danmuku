package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotNull

/**
 * 删除弹幕接口载荷
 */
object AdminInteractDelDanmuku {

    data class Request(
        /** 弹幕ID */
        @field:NotNull(message = "弹幕ID不能为空")
        val danmuId: Long? = null
    )

    class Response
}
