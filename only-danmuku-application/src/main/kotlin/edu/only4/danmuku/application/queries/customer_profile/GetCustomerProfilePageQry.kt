package edu.only4.danmuku.application.queries.customer_profile

import com.only4.cap4k.ddd.core.application.query.PageQueryParam

/**
 * 按状态获取用户列表
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
object GetCustomerProfilePageQry {

    data class Request(
        var nickNameFuzzy: String? = null,
        var status: Int? = null
    ) : PageQueryParam<UserItem>()

    data class UserItem(
        var userId: Long,
        var avatar: String?,
        var nickName: String?,
        var email: String?,
        var birthday: String?,
        var joinTime: Long,
        var lastLoginTime: Long?,
        var sex: Int?,
        var lastLoginIp: String?,
        var personIntroduction: String?,
        var currentCoinCount: Int?,
        var totalCoinCount: Int?,
        var status: Int?,
    )
}
