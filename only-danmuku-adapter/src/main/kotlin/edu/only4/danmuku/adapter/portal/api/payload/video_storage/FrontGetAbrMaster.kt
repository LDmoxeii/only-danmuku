package edu.only4.danmuku.adapter.portal.api.payload.video_storage

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 前台：获取 ABR master.m3u8（URL 或内容）
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
object FrontGetAbrMaster {

    data class Request(
        val fileId: Long
    )

    data class Response(
        val url: String?,
        val content: String?
    )

    @Mapper(componentModel = "default")
    interface Converter {
        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
