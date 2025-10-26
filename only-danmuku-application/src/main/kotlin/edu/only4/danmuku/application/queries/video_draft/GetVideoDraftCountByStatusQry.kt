package edu.only4.danmuku.application.queries.video_draft

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 按状态统计视频草稿数量
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetVideoDraftCountByStatusQry {

    data class Request(
        /** 用户ID */
        val userId: Long,
        /** 状态 */
        val status: Int? = null,
        /** 排除状态数组 */
        val excludeStatusArray: List<Int>? = null
    ) : RequestParam<Response>

    data class Response(
        /** 数量 */
        val count: Long,
    )
}
