package edu.only4.danmuku.adapter.portal.api.payload.account

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

object AccountLogin {

    data class Request(
        @param:NotEmpty(message = "邮箱不能为空") @param:Email(message = "邮箱格式不正确") @param:Size(max = 150, message = "邮箱长度不能超过150个字符") val email: String,
        @param:NotEmpty(message = "密码不能为空") @param:Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,18}$", message = "密码必须为8-18位字母和数字组合") val password: String,
        @param:NotEmpty(message = "验证码不能为空") val checkCodeKey: String,
        @param:NotEmpty(message = "验证码不能为空") val checkCode: String,
    )

    data class Response(

        var userId: Long,

        var nickName: String,

        var avatar: String?,

        var expireAt: Long?,

        var token: String,
    )
}
