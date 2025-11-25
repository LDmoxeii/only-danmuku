package edu.only4.danmuku.adapter.portal.api.payload.video_encrypt

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 前台：获取加密 ts 片段（enc目录），用于播放器解密播放
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object FrontGetEncSegment {

    data class Request(
        val fileId: Long,
        val quality: String,
        val ts: String
    )

    data class Response(
        val segmentPath: String
    )

    @Mapper(componentModel = "default")
    interface Converter {
        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
