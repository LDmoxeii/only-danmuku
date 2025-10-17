package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 获取视频详情接口载荷
 *
 * @see edu.only4.danmuku.adapter.portal.api.VideoController.videoGetInfo
 */
object VideoGetInfo {

    /**
     * 请求参数
     */
    data class Request(
        /**
         * 视频ID
         */

        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String = null
    )

    /**
     * 响应结果
     */
    data class Response(
        /**
         * 视频信息
         */
        var videoInfo: Map<String, Any>? = null,
        /**
         * 用户行为列表
         */
        var userActionList: List<Any>? = null
    )
}
