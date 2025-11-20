package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 生成并下发短信验证码
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
object SendSmsCode {

    /**
     * 发送短信验证码请求
     */
    data class Request(
        /**
         * 场景，如 LOGIN、BIND_PHONE 等
         */
        @field:NotEmpty(message = "场景不能为空")
        val scene: String,

        /**
         * 目标手机号
         */
        @field:NotEmpty(message = "手机号不能为空")
        @field:Pattern(regexp = "^\\d{11}$", message = "手机号格式不正确")
        val phone: String,
    )

    /**
     * 发送短信验证码响应
     */
    class Response

    @Mapper(componentModel = "default")
    interface Converter {
        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
