package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

/**
 * 用户注册接口载荷
 *
 * @see edu.only4.danmuku.adapter.portal.api.AccountController.register
 */
object AccountRegister {

    /**
     * 用户注册请求参数
     */
    data class Request(
        /**
         * 邮箱地址
         */
        @field:NotEmpty(message = "邮箱不能为空")
        @field:Email(message = "邮箱格式不正确")
        @field:Size(max = 150, message = "邮箱长度不能超过150个字符")
        val email: String,

        /**
         * 用户昵称
         */
        @field:NotEmpty(message = "昵称不能为空")
        @field:Size(max = 20, message = "昵称长度不能超过20个字符")
        val nickName: String,

        /**
         * 注册密码
         */
        @field:NotEmpty(message = "密码不能为空")
        @field:Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,18}$",
            message = "密码必须为8-18位字母和数字组合"
        )
        val registerPassword: String,

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
     * 用户注册响应结果
     */
    class Response
}
