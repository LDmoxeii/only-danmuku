package edu.only4.danmuku.application.queries.customer_action

import com.only4.cap4k.ddd.core.application.RequestParam

import edu.only4.danmuku.domain.aggregates.customer_action.enums.ActionType

/**
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 */
object UniqueCustomerActionTypeQry {

    class Request(
        val videoId: Long,
        val commentId: Long?,
        val actionType: ActionType,
        val customerId: Long,
        val excludeCustomerActionId: Long?
    ) : RequestParam<Response>

    class Response(
        val exists: Boolean
    )
}
