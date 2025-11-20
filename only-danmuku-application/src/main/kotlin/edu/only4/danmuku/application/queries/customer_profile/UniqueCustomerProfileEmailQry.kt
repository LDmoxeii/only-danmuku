package edu.only4.danmuku.application.queries.customer_profile

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 该文件由 [cap4k-ddd-codegen-gradle-plugin] 生成
 * @author cap4k-ddd-codegen
 */
object UniqueCustomerProfileEmailQry {

    class Request(
        val email: String,
        val excludeCustomerProfileId: Long?
    ) : RequestParam<Response>

    class Response(
        val exists: Boolean
    )
}
