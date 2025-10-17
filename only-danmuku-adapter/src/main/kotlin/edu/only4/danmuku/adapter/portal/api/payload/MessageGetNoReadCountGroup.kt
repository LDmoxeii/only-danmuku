package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 获取未读消息数(分组)接口载荷
 */
object MessageGetNoReadCountGroup {

    class Request

    data class Response(
        /** 分组未读消息数列表 */
        var list: List<Any>? = null
    )
}
