package edu.only4.danmuku.adapter.admin.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 推荐视频接口载荷
 */
object AdminVideoRecommend {

    data class Request(
        /** 视频ID */

        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String = null
    )

    class Response
}
