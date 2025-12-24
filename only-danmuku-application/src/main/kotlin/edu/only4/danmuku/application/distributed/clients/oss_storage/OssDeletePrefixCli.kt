package edu.only4.danmuku.application.distributed.clients.oss_storage

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 删除 OSS 前缀下的对象
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/12/24
 */
object OssDeletePrefixCli {

    data class Request(
        val objectPrefix: String
    ) : RequestParam<Response>

    data class Response(
        val deletedCount: Int,
        val success: Boolean = true
    )
}
