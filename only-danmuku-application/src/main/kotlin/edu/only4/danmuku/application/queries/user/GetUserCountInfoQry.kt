package edu.only4.danmuku.application.queries.user

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取用户统计信息(粉丝数、硬币数、关注数)
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/20
 */
object GetUserCountInfoQry {

    class Request(
        val customerId: Long
    ) : RequestParam<Response>

    class Response(
        val fansCount: Long,
        val currentCoinCount: Int,
        val focusCount: Long
    )
}
