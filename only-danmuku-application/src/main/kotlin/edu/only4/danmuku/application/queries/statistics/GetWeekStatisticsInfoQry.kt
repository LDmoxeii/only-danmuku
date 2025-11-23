package edu.only4.danmuku.application.queries.statistics

import com.only4.cap4k.ddd.core.application.query.ListQueryParam
import edu.only4.danmuku.domain.aggregates.statistics.enums.StatisticsDataType

/**
 * 获取最近七天的统计数据
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetWeekStatisticsInfoQry {

    data class Request(
        val userId: Long? = null,
        val dataType: StatisticsDataType,
    ) : ListQueryParam<Response>

    data class Response(
        val date: Long,
        val count: Int
    )
}
