package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 加载热门视频列表(24小时内)接口载荷
 *
 * @see edu.only4.danmuku.adapter.portal.api.VideoController.videoLoadHot
 */
object VideoLoadHot {

    /**
     * 请求参数
     */
    data class Request(
        /**
         * 页码
         */
        val pageNo: Int? = null
    )

    /**
     * 响应结果
     */
    data class Response(
        /**
         * 热门视频列表
         */
        var list: List<Any>? = null,
        /**
         * 当前页码
         */
        var pageNo: Int? = null,
        /**
         * 总记录数
         */
        var totalCount: Int? = null
    )
}
