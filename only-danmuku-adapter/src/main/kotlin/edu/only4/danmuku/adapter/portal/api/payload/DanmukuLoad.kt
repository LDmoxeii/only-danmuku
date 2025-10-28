package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 加载弹幕列表接口载荷
 */
object DanmukuLoad {

    data class Request(
        /** 文件ID */
        @field:NotEmpty(message = "文件ID不能为空")
        val fileId: String = "",
        /** 视频ID */
        @field:NotEmpty(message = "视频ID不能为空")
        val videoId: String = ""
    )

    /**
     * 弹幕项
     */
    data class DanmukuItem(
        /** 弹幕ID */
        var danmukuId: String? = null,
        /** 文件ID */
        var fileId: String? = null,
        /** 视频ID */
        var videoId: String? = null,
        /** 用户ID */
        var userId: String? = null,
        /** 弹幕内容 */
        var text: String? = null,
        /** 弹幕模式 */
        var mode: Int? = null,
        /** 弹幕颜色 */
        var color: String? = null,
        /** 弹幕时间(秒) */
        var time: Int? = null,
        /** 发布时间 */
        var postTime: String? = null,
        /** 视频名称 */
        var videoName: String? = null,
        /** 视频封面 */
        var videoCover: String? = null,
        /** 发送者昵称 */
        var nickName: String? = null,
    )

    // 弹幕无用户行为附加返回
}
