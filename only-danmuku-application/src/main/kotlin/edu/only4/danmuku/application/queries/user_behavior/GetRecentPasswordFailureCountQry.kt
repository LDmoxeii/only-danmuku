package edu.only4.danmuku.application.queries.user_behavior

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 统计用户在指定时间窗口内的密码失败次数
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
object GetRecentPasswordFailureCountQry {

    data class Request(
        val userId: Long?,
        val loginName: String,
        /**
         * 统计窗口（秒），默认 30 分钟
         */
        val windowSeconds: Long = 30 * 60,
        /**
         * 统计基准时间戳（秒），默认当前时间
         */
        val now: Long? = null,
    ) : RequestParam<Response>

    data class Response(
        val failureCount: Long
    )
}
