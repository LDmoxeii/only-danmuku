package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 获取系统设置接口载荷
 */
object AdminSettingGet {

    class Request

    data class Response(
        /** 系统设置对象 */
        var settingDto: Map<String, Any>? = null
    )
}
