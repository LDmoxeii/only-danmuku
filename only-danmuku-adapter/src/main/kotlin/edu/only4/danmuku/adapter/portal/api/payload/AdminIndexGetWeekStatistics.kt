package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 获取周统计信息接口载荷
 */
object AdminIndexGetWeekStatistics {

    data class Request(
        /** 数据类型 */
        val dataType: Int? = null
    )

    data class Response(
        /** 7天统计数据列表 */
        var list: List<Any>? = null
    )
}
