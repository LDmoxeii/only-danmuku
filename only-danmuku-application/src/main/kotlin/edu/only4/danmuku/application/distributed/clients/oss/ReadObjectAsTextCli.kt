package edu.only4.danmuku.application.distributed.clients.oss

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 读取 OSS 对象为文本
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/17
 */
object ReadObjectAsTextCli {

    data class Request(
        val objectKey: String
    ) : RequestParam<Response>

    class Response(
        val content: String
    )
}
