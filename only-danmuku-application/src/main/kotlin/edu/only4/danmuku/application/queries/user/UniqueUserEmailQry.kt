package edu.only4.danmuku.application.queries.user

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 帐号;
 *
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/04
 */
object UniqueUserEmailQry {

    class Request(
        val email: String,
        val excludeUserId: Long?
    ) : RequestParam<Response>

    class Response(
        val exists: Boolean
    )
}
