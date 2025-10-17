package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 获取视频编辑信息接口载荷
 */
object UCenterGetVideoByVideoId {

    data class Request(
        /** 视频ID */

        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String = ""
    )

    data class Response(
        /** 视频信息 */
        var videoInfo: Map<String, Any>? = null,
        /** 视频文件列表 */
        var videoInfoFileList: List<Any>? = null
    )
}
