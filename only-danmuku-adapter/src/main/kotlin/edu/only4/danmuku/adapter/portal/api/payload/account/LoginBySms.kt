package edu.only4.danmuku.adapter.portal.api.payload.account

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 使用短信验证码登录
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
object LoginBySms {

    data class Request(
        @field:NotEmpty(message = "手机号不能为空")
        @field:Pattern(regexp = "^\\d{11}$", message = "手机号格式不正确")
        val phone: String,

        @field:NotEmpty(message = "短信验证码不能为空")
        val smsCode: String,

        @field:NotEmpty(message = "验证码ID不能为空")
        val captchaId: String,
    )

    data class Response(
        val userId: Long,
        val nickName: String,
        val avatar: String?,
        val token: String,
        val expireAt: Long,
    )

    @Mapper(componentModel = "default")
    interface Converter {
        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
