package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 获取周统计信息接口载荷
 */
object UCenterGetWeekStatistics {

    data class Request(
        /** 数据类型 */
        val dataType: Int? = null
    )

    /**
     * 统计项
     */
    data class StatisticsItem(
        /** 统计日期 */
        var statisticsDate: String? = null,
        /** 统计数量 */
        var statisticsCount: Int? = null
    )
}
