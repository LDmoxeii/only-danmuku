package edu.only4.danmuku.application.queries.user

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 */
object UniqueUserPhoneQry {

    class Request(
        val phone: String?,
        val excludeUserId: Long?
    ) : RequestParam<Response>

    class Response(
        val exists: Boolean
    )
}
