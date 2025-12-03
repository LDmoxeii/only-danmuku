package edu.only4.danmuku.application.queries.video_hls_key_token

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 */
object UniqueVideoHlsKeyTokenQry {

    class Request(
        val tokenHash: String,
        val excludeVideoHlsKeyTokenId: Long?
    ) : RequestParam<Response>

    class Response(
        val exists: Boolean
    )
}
