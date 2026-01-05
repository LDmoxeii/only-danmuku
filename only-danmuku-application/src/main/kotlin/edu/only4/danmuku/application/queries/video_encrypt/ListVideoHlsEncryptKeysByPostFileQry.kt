package edu.only4.danmuku.application.queries.video_encrypt

import com.only4.cap4k.ddd.core.application.query.ListQueryParam

/**
 * 按 videoPostId + fileIndex 查询密钥ID列表（转正绑定用）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
object ListVideoHlsEncryptKeysByPostFileQry {

    data class Request(
        val videoPostId: Long,
        val fileIndex: Int
    ) : ListQueryParam<Response>

    data class Response(
        val encryptKeyId: Long
    )
}
