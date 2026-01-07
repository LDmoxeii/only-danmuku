package edu.only4.danmuku.adapter.portal.api.payload.u_home

/**
 * 获取用户信息接口载荷
 */
object SaveTheme {

    data class Request(
        val theme: Int
    )
}
