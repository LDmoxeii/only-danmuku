package edu.only4.danmuku.adapter.portal.api.payload.video_encrypt

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * 前台：申请播放 token
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object FrontIssueEncToken {

    data class Request(
        val fileId: Long
    )

    data class Response(
        val token: String,
        val expireAt: Long,
        val allowedQualities: String?
    )

    @Mapper(componentModel = "default")
    interface Converter {
        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
