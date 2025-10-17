package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 加载推荐视频接口载荷
 *
 * @see edu.only4.danmuku.adapter.portal.api.VideoController.videoLoadRecommend
 */
object VideoLoadRecommend {

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
         * 推荐视频列表
         */
        var list: List<Any>? = null
    )
}
