package edu.only4.danmuku.application.queries.video_hls_quality_auth

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 */
object UniqueVideoHlsQualityAuthQry {

    class Request(
        val fileId: Long,
        val quality: String,
        val excludeVideoHlsQualityAuthId: Long?
    ) : RequestParam<Response>

    class Response(
        val exists: Boolean
    )
}
