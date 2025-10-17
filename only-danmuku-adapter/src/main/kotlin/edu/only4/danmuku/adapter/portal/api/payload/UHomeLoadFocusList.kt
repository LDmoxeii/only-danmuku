package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.PageParam

/**
 * 加载关注列表接口载荷
 */
object UHomeLoadFocusList {

    data class Request(
        val userId: String? = null
    ) : PageParam()

    data class UserItem(
        var userId: String? = null,
        var nickName: String? = null,
        var avatar: String? = null,
        var fansCount: Int? = null,
        var haveFocus: Boolean? = null
    )
}
