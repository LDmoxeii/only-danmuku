package edu.only4.danmuku.adapter.portal.api.payload

/**
 * 自动登录接口载荷
 *
 * @see edu.only4.danmuku.adapter.portal.api.AccountController.autoLogin
 */
object AccountAutoLogin {

    /**
     * 自动登录请求参数
     */
    class Request {
        // 无请求参数，通过Cookie中的Token进行验证
    }

    /**
     * 自动登录响应结果
     */
    data class Response(
        /**
         * 用户ID
         */
        var userId: String? = null,

        /**
         * 用户昵称
         */
        var nickName: String? = null,

        /**
         * 用户头像
         */
        var avatar: String? = null,

        /**
         * 登录令牌
         */
        var token: String? = null,

        /**
         * 令牌过期时间戳
         */
        var expireAt: Long? = null
    )
}
