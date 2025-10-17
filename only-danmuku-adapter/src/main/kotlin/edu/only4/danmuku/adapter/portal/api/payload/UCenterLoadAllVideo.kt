package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 加载所有视频接口载荷
 */
object UCenterLoadAllVideo {

    class Request

    data class Response(
        /** 视频列表 */
        var list: List<Any>? = null
    )
}
