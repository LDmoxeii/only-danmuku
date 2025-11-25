package edu.only4.danmuku.adapter.portal.api.payload.video_transcode

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 后台：获取 ABR master 路径（稿件态）
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object AdminGetAbrMaster {

    data class Request(
        val fileId: Long
    )

    data class Response(
        val status: String,
        val masterPath: String?
    )

    @Mapper(componentModel = "default")
    interface Converter {
        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
