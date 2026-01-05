package edu.only4.danmuku.adapter.portal.api.payload.video_post_processing

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 处理聚合文件创建参数（不暴露子实体ID）
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
object VideoPostProcessingFileSpec {

    data class Request(
        val uploadId: Long,
        val fileIndex: Int,
        val fileName: String?,
        val fileSize: Long?,
        val duration: Int?
    )

    class Response

    @Mapper(componentModel = "default")
    interface Converter {
        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
