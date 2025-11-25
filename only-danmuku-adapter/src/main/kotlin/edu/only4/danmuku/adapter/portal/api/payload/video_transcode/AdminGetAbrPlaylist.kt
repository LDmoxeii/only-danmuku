package edu.only4.danmuku.adapter.portal.api.payload.video_transcode

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 后台：获取指定档位 m3u8 路径（稿件态）
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object AdminGetAbrPlaylist {

    data class Request(
        val fileId: Long,
        val quality: String
    )

    data class Response(
        val playlistPath: String
    )

    @Mapper(componentModel = "default")
    interface Converter {
        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}

