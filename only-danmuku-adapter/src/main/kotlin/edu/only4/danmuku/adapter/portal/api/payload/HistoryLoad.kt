package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 加载播放历史接口载荷
 */
object HistoryLoad {

    data class Request(
        /** 页码 */
        val pageNo: Int? = null
    )

    data class Response(
        /** 播放历史列表 */
        var list: List<Any>? = null,
        var pageNo: Int? = null,
        var totalCount: Int? = null
    )
}
