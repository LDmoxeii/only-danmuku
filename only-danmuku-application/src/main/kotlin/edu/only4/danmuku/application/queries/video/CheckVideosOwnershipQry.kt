package edu.only4.danmuku.application.queries.video

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 检查一组视频是否全部属于指定用户
 */
object CheckVideosOwnershipQry {

    data class Request(
        val userId: Long,
        val videoIds: List<Long>,
    ) : RequestParam<Response>

    data class Response(
        val allOwned: Boolean,
        val missing: List<Long>,
    )
}

