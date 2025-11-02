package edu.only4.danmuku.adapter.portal.api.payload

import com.only4.cap4k.ddd.core.share.PageParam
import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation

/**
 * 加载评论列表(分页)接口载荷
 */
object AdminInteractLoadComment {

    class Request(
        val videoNameFuzzy: String? = null
    ) : PageParam()

    data class Response(
        var commentId: Long? = null,
        var pCommentId: Long? = null,
        var videoId: String? = null,
        var videoName: String? = null,
        var videoCover: String? = null,
        var userId: String? = null,
        var nickName: String? = null,
        var avatar: String? = null,
        var replyUserId: String? = null,
        var replyNickName: String? = null,
        var content: String? = null,
        var imgPath: String? = null,
        var topType: Int? = null,
        var likeCount: Int? = null,
        var hateCount: Int? = null,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var postTime: Long? = null
    )
}

