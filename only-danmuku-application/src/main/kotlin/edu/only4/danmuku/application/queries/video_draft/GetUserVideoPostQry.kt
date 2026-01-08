package edu.only4.danmuku.application.queries.video_draft

import com.only4.cap4k.ddd.core.application.query.PageQueryParam
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus

/**
 * 获取用户视频稿件列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetUserVideoPostQry {

    data class Request(
        var userId: Long,
        var status: VideoStatus? = null,
        var videoNameFuzzy: String? = null,
        var excludeStatusArray: List<VideoStatus>? = null
    ) : PageQueryParam<Response>()

    data class Response(
        val videoPostId: Long,
        val videoId: Long?,
        val videoCover: String,
        val videoName: String,
        val duration: Int?,
        val createTime: Long,
        val lastUpdateTime: Long?,
        val status: VideoStatus,
        val interaction: String? = null,
        val playCount: Int? = 0,
        val likeCount: Int? = 0,
        val danmukuCount: Int? = 0,
        val commentCount: Int? = 0,
        val coinCount: Int? = 0,
        val collectCount: Int? = 0,
    )
}
