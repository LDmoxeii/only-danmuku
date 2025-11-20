package edu.only4.danmuku.adapter.portal.api.payload

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

    class Request

    class Response

    @Mapper(componentModel = "default")
    interface Converter {
        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}

