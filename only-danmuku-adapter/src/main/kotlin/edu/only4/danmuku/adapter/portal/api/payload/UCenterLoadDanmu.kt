package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.PageParam

/**
 * 加载用户收到的弹幕接口载荷
 */
object UCenterLoadDanmu {

    data class Request(
        /** 视频ID */
        val videoId: String? = null
    ) : PageParam()

    /**
     * 弹幕项
     */
    data class DanmukuItem(
        /** 弹幕ID */
        var danmukuId: String? = null,
        /** 视频ID */
        var videoId: String? = null,
        /** 视频名称 */
        var videoName: String? = null,
        /** 弹幕内容 */
        var text: String? = null,
        /** 用户ID */
        var userId: String? = null,
        /** 弹幕模式 */
        var mode: Int? = null,
        /** 弹幕颜色 */
        var color: String? = null,
        /** 弹幕时间 */
        var time: Int? = null,
        /** 发布时间 */
        var postTime: String? = null
    )
}
