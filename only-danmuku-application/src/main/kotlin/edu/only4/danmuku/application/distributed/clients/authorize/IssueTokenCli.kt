package edu.only4.danmuku.application.distributed.clients.authorize

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 签发 Token
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/17
 */
object IssueTokenCli {

    data class Request(
        val userId: Long,
        val accountType: Int,
        val account: String,
        val extra: Map<String, Any> = emptyMap()
    ) : RequestParam<Response>

    data class Response(
        val token: String
    )
}
