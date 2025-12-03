package edu.only4.danmuku.application.queries.video_encrypt

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 查询可用的最新密钥元数据（不返回明文），用于拼接 m3u8 key URI
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
object GetLatestVideoHlsKeyQry {

    data class Request(
        val videoFilePostId: Long? = null,
        val videoFileId: Long? = null
    ) : RequestParam<Response>

    data class Response(
        val keyId: String?,
        val keyVersion: Int?,
        val keyQuality: String?,
        val keyUriTemplate: String?,
        val status: String
    )
}
