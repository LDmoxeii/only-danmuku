package edu.only4.danmuku.adapter.portal.api.payload.video_encrypt

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 前台：根据 token 获取明文 AES key
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object FrontGetEncKey {

    data class Request(
        val keyId: String,
        val token: String,
        val quality: String
    )

    data class Response(
        val keyBytes: String
    )

    @Mapper(componentModel = "default")
    interface Converter {
        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
