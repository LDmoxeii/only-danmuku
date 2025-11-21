package edu.only4.danmuku.application.queries.video_file_post

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
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
