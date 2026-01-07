package edu.only4.danmuku.adapter.portal.api.payload.user_action

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

object DoAction {

    data class Request(
        val videoId: Long,
        val actionType: Int,
        @param:Max(2) @param:Min(1) val actionCount: Int = 1,
        val commentId: Long?,
    )
}
