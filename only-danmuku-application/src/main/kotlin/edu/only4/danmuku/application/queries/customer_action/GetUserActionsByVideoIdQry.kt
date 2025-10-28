package edu.only4.danmuku.application.queries.customer_action

import com.only4.cap4k.ddd.core.application.query.ListQueryParam

/**
 * 获取指定用户在指定视频下的行为列表
 */
object GetUserActionsByVideoIdQry {

    data class Request(
        val userId: Long,
        val videoId: Long,
    ) : ListQueryParam<Response>

    data class Response(
        val actionId: Long,
        val userId: Long,
        val videoId: Long,
        val videoName: String,
        val videoCover: String,
        val videoUserId: Long,
        val commentId: Long?,
        val actionType: Int,
        val actionCount: Int,
        val actionTime: Long,
    )
}

