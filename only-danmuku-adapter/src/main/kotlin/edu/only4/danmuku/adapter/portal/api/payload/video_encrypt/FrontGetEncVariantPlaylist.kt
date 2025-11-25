package edu.only4.danmuku.adapter.portal.api.payload.video_encrypt

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 前台：获取指定档位加密 m3u8（已替换 token）
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object FrontGetEncVariantPlaylist {

    data class Request(
        val fileId: Long,
        val quality: String,
        val token: String
    )

    data class Item(
        val content: String
    )

    @Mapper(componentModel = "default")
    interface Converter {
        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}

