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
        /**
         * 用户ID
         */
        val customerId: Long
    ) : RequestParam<Response>

    class Response(
        /**
         * 粉丝数
         */
        val fansCount: Int,

        /**
         * 当前硬币数
         */
        val currentCoinCount: Int,

        /**
         * 关注数
         */
        val focusCount: Int
    )
}
