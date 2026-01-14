package edu.only4.danmuku.application.queries.video_quality_policy

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 */
object UniqueVideoQualityPolicyQry {

    class Request(
        val videoId: Long,
        val fileIndex: Int,
        val quality: String,
        val excludeVideoQualityPolicyId: Long?
    ) : RequestParam<Response>

    class Response(
        val exists: Boolean
    )
}
