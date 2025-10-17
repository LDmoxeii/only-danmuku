package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 加载用户发布的视频列表接口载荷
 */
object UCenterLoadVideoList {

    data class Request(
        /** 状态(-1进行中) */
        val status: Int? = null,
        /** 页码 */
        val pageNo: Int? = null,
        /** 视频名称模糊查询 */
        val videoNameFuzzy: String? = null
    )

    data class Response(
        /** 视频列表 */
        var list: List<Any>? = null,
        var pageNo: Int? = null,
        var totalCount: Int? = null
    )
}
