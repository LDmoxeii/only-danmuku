package edu.only4.danmuku.adapter.portal.api.payload.admin_account

import jakarta.validation.constraints.NotEmpty

/**
 * 管理员登录接口载荷
 */
object Login {

    data class Request(
        /** 管理员账号 */

        @field:NotEmpty(message = "管理员账号不能为空")
        val account: String = "",
        /** 管理员密码(MD5加密) */

        @field:NotEmpty(message = "管理员密码(MD5加密)不能为空")
        val password: String = "",
        /** 验证码 */

        @field:NotEmpty(message = "验证码不能为空")
        val checkCode: String = "",
        /** 验证码唯一键 */

        @field:NotEmpty(message = "验证码唯一键不能为空")
        val checkCodeKey: String = ""
    )

    data class Response(
        /**
         * 用户ID
         */
        var userId: Long,

        /** 管理员账号 */
        var account: String? = null,

        /**
         * 用户昵称
         */
        var nickName: String,

        /**
         * 用户头像
         */
        var avatar: String? = null,

        /**
         * 登录令牌
         */
        var token: String
    )
}
