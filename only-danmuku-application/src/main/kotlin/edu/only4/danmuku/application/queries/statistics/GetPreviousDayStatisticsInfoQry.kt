package edu.only4.danmuku.application.queries.statistics

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 获取前一天的统计数据
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetPreviousDayStatisticsInfoQry {

    data class Request(
        val userId: Long? = null,
    ) : RequestParam<Response>

    data class Response(
        val userCount: Int = 0,
        val playCount: Int = 0,
        val commentCount: Int = 0,
        val danmukuCount: Int = 0,
        val likeCount: Int = 0,
        val collectCount: Int = 0,
        val coinCount: Int = 0,
    )
}
