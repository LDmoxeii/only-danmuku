package edu.only4.danmuku.application.queries.video

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取视频信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetVideoInfoQry {

    data class Request(
        /** 视频ID */
        val videoId: Long
    ) : RequestParam<Response>

    data class Response(
        var videoId: Long,
        var videoCover: String,
        var videoName: String,
        var userId: Long,
        var createTime: Long,
        var postType: Int,
        var originInfo: String?,
        var tags: String?,
        var introduction: String? = null,
        var interaction: String? = null,
        var playCount: Int? = null,
        var likeCount: Int? = null,
        var danmuCount: Int? = null,
        var commentCount: Int? = null,
        var coinCount: Int? = null,
        var collectCount: Int? = null,
        val nickName: String? = null,
        val avatar: String? = null,
        val duration: Int? = null,
    )
}
