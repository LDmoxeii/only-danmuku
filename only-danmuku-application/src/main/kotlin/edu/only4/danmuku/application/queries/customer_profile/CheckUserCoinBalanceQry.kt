package edu.only4.danmuku.application.queries.customer_profile

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 检查用户硬币余额是否充足
 */
object CheckUserCoinBalanceQry {

    data class Request(
        /** 用户ID */
        val userId: Long,
        /** 需要的硬币数量 */
        val requiredAmount: Int
    ) : RequestParam<Response>

    data class Response(
        /** 余额是否充足 */
        val sufficient: Boolean,
        /** 当前硬币余额 */
        val currentBalance: Int
    )
}
