package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 删除指定播放历史接口载荷
 */
object HistoryDel {

    data class Request(
        /** 视频ID */

        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String = null
    )

    class Response
}
