package edu.only4.danmuku.application.queries.user

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 检查登录
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object CheckLoginQry {

    class Request(
        val email: String,
        val password: String,
    ) : RequestParam<Response>

    class Response(
        val userId: Long,
    )
}
