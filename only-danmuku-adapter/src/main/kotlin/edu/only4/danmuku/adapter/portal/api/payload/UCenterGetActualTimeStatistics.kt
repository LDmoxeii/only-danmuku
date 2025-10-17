package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 获取实时统计信息接口载荷
 */
object UCenterGetActualTimeStatistics {

    class Request

    data class Response(
        /** 前一天数据 */
        var preDayData: Map<String, Any>? = null,
        /** 总统计信息 */
        var totalCountInfo: Map<String, Any>? = null
    )
}
