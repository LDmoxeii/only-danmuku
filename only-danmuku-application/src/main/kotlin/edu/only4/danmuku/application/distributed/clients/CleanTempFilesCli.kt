package edu.only4.danmuku.application.distributed.clients

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 清理视频草稿相关的临时文件
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/02
 */
object CleanTempFilesCli {

    class Request(
        val tempPaths: List<String>,
    ) : RequestParam<Response>

    class Response(
        val cleaned: Boolean,
    )
}
