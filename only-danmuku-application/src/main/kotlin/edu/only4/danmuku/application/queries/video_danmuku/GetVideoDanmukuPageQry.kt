package edu.only4.danmuku.application.queries.video_danmuku

import com.only4.cap4k.ddd.core.application.query.PageQueryParam

/**
 * 获取视频弹幕分页列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/17
 */
object GetVideoDanmukuPageQry {

    data class Request(
        var videoUserId: Long? = null,
        var videoNameFuzzy: String? = null,
    ) : PageQueryParam<Response>()

    data class Response(
        val danmukuId: Long,
        val videoId: Long,
        val videoName: String,
        val videoCover: String,
        val customerId: Long,
        val customerNickname: String,
        val text: String,
        val mode: Int,
        val color: String,
        val time: Int,
        val postTime: Long,
    )
}
