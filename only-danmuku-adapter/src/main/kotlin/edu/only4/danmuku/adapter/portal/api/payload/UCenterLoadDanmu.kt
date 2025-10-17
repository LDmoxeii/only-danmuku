package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 加载用户收到的弹幕接口载荷
 */
object UCenterLoadDanmu {

    data class Request(
        /** 页码 */
        val pageNo: Int? = null,
        /** 视频ID */
        val videoId: String? = null
    )

    data class Response(
        /** 弹幕列表 */
        var list: List<Any>? = null,
        var pageNo: Int? = null,
        var totalCount: Int? = null
    )
}
