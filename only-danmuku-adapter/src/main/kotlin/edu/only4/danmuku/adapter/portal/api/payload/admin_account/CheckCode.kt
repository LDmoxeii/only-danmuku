package edu.only4.danmuku.adapter.portal.api.payload.admin_account

/**
 * 获取管理员登录验证码接口载荷
 */
object CheckCode {

    class Request

    data class Response(
        /** 验证码Base64编码 */
        var checkCode: String,
        /** 验证码唯一键 */
        var checkCodeKey: String? = null,
    )
}
