package edu.only4.danmuku.application.queries.user

import com.only4.cap4k.ddd.core.application.RequestParam

/**
 * 检查昵称是否存在
 */
object CheckNicknameExistsQry {

    class Request(
        val nickName: String,
        val excludeUserId: Long? = null,
    ) : RequestParam<Response>

    class Response(
        val exists: Boolean,
    )
}
