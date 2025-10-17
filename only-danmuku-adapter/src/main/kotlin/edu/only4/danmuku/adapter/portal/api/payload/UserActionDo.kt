package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty

/**
 * 执行用户行为(点赞/收藏/投币)接口载荷
 */
object UserActionDo {

    data class Request(
        /** 视频ID */

        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String = "",
        /** 行为类型 */

        @field:NotEmpty(message = "行为类型不能为空")
        val actionType: Int = 0,
        /** 行为次数 */

        @field:Min(1, message = "最小值为1")
        @field:Max(2, message = "最大值为2")
        val actionCount: Int? = null,
        /** 评论ID */
        val commentId: Int? = null
    )

    class Response
}
