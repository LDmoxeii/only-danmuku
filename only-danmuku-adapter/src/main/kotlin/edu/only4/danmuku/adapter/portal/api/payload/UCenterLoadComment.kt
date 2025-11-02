package edu.only4.danmuku.adapter.portal.api.payload

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import com.only4.cap4k.ddd.core.share.PageParam

/**
 * 加载用户收到的评论接口载荷
 */
object UCenterLoadComment {

    data class Request(
        val videoId: String? = null
    ) : PageParam()

    data class CommentItem(
        var commentId: String? = null,
        var videoId: String? = null,
        var videoName: String? = null,
        var content: String? = null,
        var userId: String? = null,
        var nickName: String? = null,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var postTime: Long
    )
}
