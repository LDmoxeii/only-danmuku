package edu.only4.danmuku.application.queries.video_danmuku

import com.only4.cap4k.ddd.core.application.query.ListQueryParam

/**
 * 根据文件ID获取弹幕
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetDanmukuByFileIdQry {

    data class Request(
        /** 文件ID */
        val fileId: Long,
        /** 视频ID */
        val videoId: Long
    ) : ListQueryParam<Response>

    data class Response(
        /** 弹幕ID */
        val danmukuId: Long,
        /** 文件ID */
        val fileId: Long,
        /** 视频ID */
        val videoId: Long,
        /** 用户ID */
        val userId: Long,
        /** 弹幕内容 */
        val text: String,
        /** 弹幕模式 */
        val mode: Int,
        /** 弹幕颜色 */
        val color: String,
        /** 弹幕时间(秒) */
        val time: Int,
        /** 发布时间 */
        val postTime: Long
    )
}
