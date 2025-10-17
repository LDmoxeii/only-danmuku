package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 上报在线播放数接口载荷
 *
 * @see edu.only4.danmuku.adapter.portal.api.VideoController.videoReportPlayOnline
 */
object VideoReportPlayOnline {

    /**
     * 请求参数
     */
    data class Request(
        /**
         * 文件ID
         */

        @field:NotEmpty(message = "文件ID不能为空")
        val fileId: String = "",
        /**
         * 设备ID
         */
        val deviceId: String? = null
    )

    /**
     * 响应结果
     */
    data class Response(
        /**
         * 在线人数
         */
        var count: Int? = null
    )
}
