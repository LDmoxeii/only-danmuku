package edu.only4.danmuku.adapter.portal.api.payload

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import com.only4.cap4k.ddd.core.share.PageParam

/**
 * 加载用户列表(分页)接口载荷
 */
object AdminUserLoad {

    class Request(
        /** 昵称模糊查询 */
        val nickNameFuzzy: String? = null,
        /** 用户状态 */
        val status: Int? = null,
    ) : PageParam()

    /**
     * 用户项
     */
    data class UserItem(
        var userId: Long,
        var avatar: String?,
        var nickName: String?,
        var email: String?,
        var birthday: String?,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var joinTime: Long,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var lastLoginTime: Long?,
        var sex: Int?,
        var lastLoginIp: String?,
        var personIntroduction: String?,
        var currentCoinCount: Int?,
        var totalCoinCount: Int?,
        var status: Int?,
    )
}
