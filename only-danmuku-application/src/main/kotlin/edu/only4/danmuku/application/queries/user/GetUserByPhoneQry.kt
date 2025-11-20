package edu.only4.danmuku.application.queries.user

import com.only4.cap4k.ddd.core.application.RequestParam
import edu.only4.danmuku.domain.aggregates.user.enums.UserType

/**
 * 通过手机号获取对应的用户账号信息
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/20
 */
object GetUserByPhoneQry {

    class Request(
        val phone: String,
    ) : RequestParam<Response>

    class Response(
        val userId: Long,
        val nickName: String,
        val type: UserType,
    )
}
