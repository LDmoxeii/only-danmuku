package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 删除弹幕接口载荷
 */
object UCenterDelDanmu {

    data class Request(
        /** 弹幕ID */

        @field:NotEmpty(message = "弹幕ID不能为空")
        val danmuId: Int = 0
    )

    class Response
}
