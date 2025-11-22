package edu.only4.danmuku.adapter.portal.api.payload

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import com.only4.cap4k.ddd.core.share.PageParam
import edu.only4.danmuku.application.queries.video_comment.VideoCommentPageQry
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * 加载用户收到的评论接口载荷
 */
object UCenterLoadComment {

    data class Request(
        val videoId: String? = null
    ) : PageParam()

    data class CommentItem(
        var commentId: String? = null,
        val avatar: String? = null,
        var videoId: String? = null,
        var videoName: String? = null,
        val videoCover: String,
        var content: String? = null,
        val imgPath: String? = null,
        var userId: String? = null,
        var nickName: String? = null,
        val replyNickName: String? = null,
        @get:Translation(type = EpochSecondToDateStringTranslation.TYPE, other = "yyyy-MM-dd HH:mm:ss")
        var postTime: Long
    )

    @Mapper(componentModel = "default")
    interface Converter {
        @Mapping(source = "commentId", target = "commentId")
        @Mapping(source = "videoId", target = "videoId")
        @Mapping(source = "customerId", target = "userId")
        @Mapping(source = "customerNickname", target = "nickName")
        @Mapping(source = "customerAvatar", target = "avatar")
        @Mapping(source = "replyCustomerNickname", target = "replyNickName")
        fun fromApp(resp: VideoCommentPageQry.Response): CommentItem

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
