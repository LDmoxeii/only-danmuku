package edu.only4.danmuku.adapter.portal.api.payload

import edu.only4.danmuku.application.commands.user.ChangePasswordCmd
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * 用户修改登录密码
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
object ChangePassword {

    /**
     * 修改密码请求参数
     */
    data class Request(
        /**
         * 原密码
         */
        @field:NotEmpty(message = "原密码不能为空")
        @field:Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,18}$",
            message = "原密码必须为8-18位字母和数字组合"
        )
        val oldPassword: String,

        /**
         * 新密码
         */
        @field:NotEmpty(message = "新密码不能为空")
        @field:Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,18}$",
            message = "新密码必须为8-18位字母和数字组合"
        )
        val newPassword: String,
    )

    /**
     * 修改密码响应结果
     */
    class Response

    @Mapper(componentModel = "default")
    interface Converter {

        @Mapping(target = "userId", source = "userId")
        fun toCmd(request: Request, userId: Long): ChangePasswordCmd.Request

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
