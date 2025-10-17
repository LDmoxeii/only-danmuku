package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 保存用户主题接口载荷
 */
object UHomeSaveTheme {

    data class Request(
        /** 主题 */
        val theme: Int? = null
    )

    class Response
}
