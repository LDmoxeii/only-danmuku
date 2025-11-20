package edu.only4.danmuku.adapter.portal.api.payload

import edu.only4.danmuku.application.commands.customer_profile.BindPhoneCmd
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 绑定/变更用户手机号
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
object BindPhone {

    data class Request(
        @field:NotEmpty(message = "手机号不能为空")
        @field:Pattern(regexp = "^\\d{11}$", message = "手机号格式不正确")
        val phone: String,

        @field:NotEmpty(message = "短信验证码不能为空")
        val smsCode: String,

        @field:NotEmpty(message = "验证码ID不能为空")
        val captchaId: String,
    )

    class Response

    @Mapper(componentModel = "default")
    interface Converter {
        fun toCmd(request: Request, userId: Long): BindPhoneCmd.Request

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
