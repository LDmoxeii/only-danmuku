package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

/**
 * 发送弹幕接口载荷
 */
object DanmuPost {

    data class Request(
        /** 视频ID */

        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String = "",
        /** 文件ID */

        @field:NotEmpty(message = "文件ID不能为空")
        val fileId: String = "",
        /** 弹幕内容 */

        @field:NotEmpty(message = "弹幕内容不能为空")
        @field:Size(max = 200, message = "长度不能超过200个字符")
        val text: String = "",
        /** 弹幕模式 */
        val mode: Int? = null,
        /** 弹幕颜色 */
        @field:NotEmpty(message = "弹幕颜色不能为空")
        val color: String = "",
        /** 弹幕时间(秒) */
        val time: Int? = null
    )

    class Response()
}
