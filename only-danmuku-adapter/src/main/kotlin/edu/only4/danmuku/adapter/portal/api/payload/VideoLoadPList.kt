package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 加载视频分片列表接口载荷
 *
 * @see edu.only4.danmuku.adapter.portal.api.VideoController.videoLoadPList
 */
object VideoLoadPList {

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
         * 分片文件列表
         */
        var list: List<Any>? = null
    )
}
