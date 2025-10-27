package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.PageParam
import java.time.LocalDateTime

/**
 * 加载弹幕列表(分页)接口载荷
 */
object AdminInteractLoadDanmuku {

    class Request(
        /** 视频名称模糊查询 */
        val videoNameFuzzy: String? = null
    ) : PageParam()

    class Response(
        /** 弹幕ID */
        var danmukuId: Long? = null,
        /** 视频ID */
        var videoId: String? = null,
        /** 视频名称 */
        var videoName: String? = null,
        /** 视频封面 */
        var videoCover: String? = null,
        /** 用户ID */
        var userId: String? = null,
        /** 用户昵称 */
        var nickName: String? = null,
        /** 弹幕内容 */
        var text: String? = null,
        /** 展示位置 */
        var mode: Int? = null,
        /** 颜色 */
        var color: String? = null,
        /** 展示时间(秒) */
        var time: Int? = null,
        /** 发布时间 */
        var postTime: LocalDateTime? = null
    )
}
