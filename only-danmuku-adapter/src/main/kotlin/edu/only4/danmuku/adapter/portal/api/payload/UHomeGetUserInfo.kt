package edu.only4.danmuku.adapter.portal.api.payload

import jakarta.validation.constraints.NotEmpty

/**
 * 获取用户信息接口载荷
 */
object UHomeGetUserInfo {

    data class Request(
        /** 用户ID */
        @field:NotEmpty(message = "用户ID不能为空")
        val userId: String = ""
    )

    data class Response(
        /** 用户信息 */
        var userInfo: UserInfo? = null
    )

    data class UserInfo(
        var userId: String? = null,
        var nickName: String? = null,
        var avatar: String? = null,
        var sex: Int? = null,
        var birthday: String? = null,
        var school: String? = null,
        var personIntroduction: String? = null,
        var noticeInfo: String? = null,
        var theme: Int? = null,
        var currentCoinCount: Int? = null,
        var fansCount: Int? = null,
        var focusCount: Int? = null,
        var haveFocus: Boolean? = null
    )
}
