package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 加载评论列表接口载荷
 */
object CommentLoad {

    data class Request(
        /** 视频ID */

        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String = "",
        /** 页码 */
        val pageNo: Int? = null,
        /** 排序类型 */
        val orderType: Int? = null
    )

    data class Response(
        /** 评论数据 */
        var commentData: Map<String, Any>? = null,
        /** 用户行为列表 */
        var userActionList: List<Any>? = null
    )
}
