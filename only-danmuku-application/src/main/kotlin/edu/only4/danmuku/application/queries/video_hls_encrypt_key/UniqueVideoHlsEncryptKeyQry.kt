package edu.only4.danmuku.application.queries.video_hls_encrypt_key

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 */
object UniqueVideoHlsEncryptKeyQry {

    class Request(
        val videoPostId: Long,
        val fileIndex: Int,
        val keyId: String,
        val keyVersion: Int,
        val quality: String,
        val excludeVideoHlsEncryptKeyId: Long?
    ) : RequestParam<Response>

    class Response(
        val exists: Boolean
    )
}
