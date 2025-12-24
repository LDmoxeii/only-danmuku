package edu.only4.danmuku.adapter.portal.api.payload.file_storage

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 获取图片/通用资源（返回 URL 或直接内容）
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
object FrontGetFileResource {

    data class Request(
        val sourceName: String
    )

    data class Response(
        val url: String?,
        val contentType: String?,
        val exists: Boolean
    )

    @Mapper(componentModel = "default")
    interface Converter {
        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
