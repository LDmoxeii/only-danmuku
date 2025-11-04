package edu.only4.danmuku.application.queries.customer_action

import com.only4.cap4k.ddd.core.application.RequestParam
import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType

/**
 * 查询用户对视频/评论的已有行为
 * 用于实现幂等性检查（Toggle 逻辑）
 */
object GetUserActionByVideoQry {

    data class Request(
        /** 用户ID */
        val customerId: Long,
        /** 视频ID */
        val videoId: Long,
        /** 评论ID（可选，针对评论的行为） */
        val commentId: Long? = null,
        /** 行为类型（可选，不指定则查询所有类型） */
        val actionType: ActionType? = null
    ) : RequestParam<Response>

    data class Response(
        /** 是否存在该行为 */
        val hasAction: Boolean,
        /** 行为详情（如果存在） */
        val action: ActionDetail? = null
    )

    data class ActionDetail(
        /** 行为ID */
        val id: Long,
        /** 行为类型 */
        val actionType: ActionType,
        /** 行为数量 */
        val actionCount: Int,
        /** 行为时间 */
        val actionTime: Long
    )
}
