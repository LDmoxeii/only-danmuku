package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 获取周统计信息接口载荷
 */
object AdminIndexGetWeekStatistics {

    data class Request(
        /** 数据类型 */
        val dataType: Int = 0
    )

    /**
     * 周统计数据项
     */
    data class WeekStatisticsItem(
        /** 记录统计数据对应的日期，格式通常为YYYY-MM-DD */
        var statisticsDate: String,
        /** 统计数量 */
        var statisticsCount: Int,
    )
}
