package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.PageParam

/**
 * 加载粉丝列表接口载荷
 */
object UHomeLoadFansList {

    class Request : PageParam()

    data class UserItem(
        // 兼容前端使用的字段命名
        var otherUserId: String? = null,
        var otherNickName: String? = null,
        var otherPersonIntroduction: String? = null,
        var otherAvatar: String? = null,
        var focusType: Int? = null,
    )
}
