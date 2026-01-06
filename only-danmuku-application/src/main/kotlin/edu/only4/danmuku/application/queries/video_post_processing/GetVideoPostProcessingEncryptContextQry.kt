package edu.only4.danmuku.application.queries.video_post_processing

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 查询处理文件加密上下文（输出前缀/目录）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
object GetVideoPostProcessingEncryptContextQry {

    data class Request(
        val videoPostId: Long,
        val fileIndex: Int
    ) : RequestParam<Response>

    data class Response(
        val transcodeOutputPrefix: String?,
        val encryptOutputDir: String?
    )
}
