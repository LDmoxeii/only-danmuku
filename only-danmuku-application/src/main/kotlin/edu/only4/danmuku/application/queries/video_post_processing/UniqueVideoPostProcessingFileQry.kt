package edu.only4.danmuku.application.queries.video_post_processing

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 */
object UniqueVideoPostProcessingFileQry {

    class Request(
        val parentId: Long,
        val fileIndex: Int,
        val excludeVideoPostProcessingFileId: Long?
    ) : RequestParam<Response>

    class Response(
        val exists: Boolean
    )
}
