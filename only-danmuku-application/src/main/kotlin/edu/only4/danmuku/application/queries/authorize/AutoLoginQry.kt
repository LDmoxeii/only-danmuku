package edu.only4.danmuku.application.queries.authorize

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 自动登录
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/17
 */
object AutoLoginQry {

    class Request : RequestParam<Response>

    data class Response(
        val userId: Long? = null,
        val nickName: String? = null,
        val avatar: String? = null,
        val expireAt: Long? = null,
        val token: String? = null
    )
}
