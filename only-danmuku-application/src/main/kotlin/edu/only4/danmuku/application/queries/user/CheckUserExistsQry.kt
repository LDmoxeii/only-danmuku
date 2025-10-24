package edu.only4.danmuku.application.queries.user

import com.only4.cap4k.ddd.core.application.RequestParam

object CheckUserExistsQry {
    data class Request(
        val userId: Long
    ) : RequestParam<Response>

    data class Response(
        val exists: Boolean
    )
}

