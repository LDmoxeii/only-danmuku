package edu.only4.danmuku.application.queries.video_draft

import com.only4.cap4k.ddd.core.application.RequestParam
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus

/**
 * 按状态统计视频草稿数量
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetVideoDraftCountByStatusQry {

    data class Request(
        val userId: Long,
        /** 状态 */
        val status: VideoStatus? = null,
        /** 排除状态数组 */
        val excludeStatusArray: List<VideoStatus>? = null
    ) : RequestParam<Response>

    data class Response(
        /** 数量 */
        val count: Long,
    )
}
