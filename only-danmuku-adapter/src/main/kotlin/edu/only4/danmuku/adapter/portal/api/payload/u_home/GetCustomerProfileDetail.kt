package edu.only4.danmuku.adapter.portal.api.payload.u_home

import edu.only4.danmuku.domain.aggregates.customer_profile.enums.ThemeType

/**
 * 获取用户信息接口载荷
 */
object GetCustomerProfileDetail {

    data class Request(
        /** 用户ID */
        val userId: Long
    )

    data class Response(
        var userId: String? = null,
        var nickName: String? = null,
        var avatar: String? = null,
        var sex: Int? = null,
        var birthday: String? = null,
        var school: String? = null,
        var personIntroduction: String? = null,
        var noticeInfo: String? = null,
        var grade: Int? = null,
        var theme: ThemeType? = null,
        var currentCoinCount: Int? = null,
        var fansCount: Long? = null,
        var focusCount: Long? = null,
        var likeCount: Int? = null,
        var playCount: Int? = null,
        var haveFocus: Boolean? = null,
    )
}
