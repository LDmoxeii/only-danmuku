package edu.only4.danmuku.application.queries.video_post

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 视频文件信息;
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/04
 */
object UniqueVideoFilePostUploadIdQry {

    class Request(
        val uploadId: Long,
        val customerId: Long,
        val excludeVideoFilePostId: Long?
    ) : RequestParam<Response>

    class Response(
        val exists: Boolean
    )
}
