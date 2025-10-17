package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

/**
 * 用户登录接口载荷
 *
 * @see edu.only4.danmuku.adapter.portal.api.AccountController.login
 */
object AccountLogin {

    /**
     * 用户登录请求参数
     */
    data class Request(
        /**
         * 邮箱地址
         */
        @field:NotEmpty(message = "邮箱不能为空")
        @field:Email(message = "邮箱格式不正确")
        val email: String,

        /**
         * 登录密码
         */
        @field:NotEmpty(message = "密码不能为空")
        val password: String,

        /**
         * 验证码Key
         */
        @field:NotEmpty(message = "验证码Key不能为空")
        val checkCodeKey: String,

        /**
         * 验证码
         */
        @field:NotEmpty(message = "验证码不能为空")
        val checkCode: String
    )

    /**
     * 用户登录响应结果
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
        var token: String? = null
    )
}
