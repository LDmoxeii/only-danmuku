package edu.only4.danmuku.adapter.portal.api.payload.video_abr

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 前台：获取可选档位列表
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object GetVideoVariants {

    data class Request(
        val fileId: Long
    )

    data class Response(
        val qualities: List<String>,
        val variantJson: String
    )

    @Mapper(componentModel = "default")
    interface Converter {
        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
