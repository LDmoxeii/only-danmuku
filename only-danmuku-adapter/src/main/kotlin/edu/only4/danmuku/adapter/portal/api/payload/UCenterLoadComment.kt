package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 加载用户收到的评论接口载荷
 */
object UCenterLoadComment {

    data class Request(
        /** 页码 */
        val pageNo: Int? = null,
        /** 视频ID */
        val videoId: String? = null
    )

    data class Response(
        /** 评论列表 */
        var list: List<Any>? = null,
        var pageNo: Int? = null,
        var totalCount: Int? = null
    )
}
