package edu.only4.danmuku.application.queries.video_encrypt

import com.only4.cap4k.ddd.core.application.query.ListQueryParam

/**
 * 按 fileId + keyVersion 查询质量 key 列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/25
 */
object ListVideoHlsEncryptKeysQry {

    data class Request(
        val videoFilePostId: Long,
        val keyVersion: Int?
    ) : ListQueryParam<Response>

    data class Response(
        val keysJson: String
    )
}
