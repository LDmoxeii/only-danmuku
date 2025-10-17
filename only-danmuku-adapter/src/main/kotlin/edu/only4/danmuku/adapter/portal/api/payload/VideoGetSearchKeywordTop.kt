package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 获取热门搜索关键词接口载荷
 *
 * @see edu.only4.danmuku.adapter.portal.api.VideoController.videoGetSearchKeywordTop
 */
object VideoGetSearchKeywordTop {

    /**
     * 请求参数
     */
    class Request {
        // 无请求参数
    }

    /**
     * 响应结果
     */
    data class Response(
        /**
         * 热门关键词列表
         */
        var list: List<Any>? = null
    )
}
