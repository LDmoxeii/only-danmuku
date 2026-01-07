package edu.only4.danmuku.adapter.portal.api.payload.admin_interact

import com.only.engine.translation.annotation.Translation
import com.only.engine.translation.translation.EpochSecondToDateStringTranslation
import com.only4.cap4k.ddd.core.share.PageParam
import edu.only4.danmuku.application.queries.video_comment.VideoCommentPageQry
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * 加载评论列表(分页)接口载荷
 */
object GetVideoCommentPage {

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

    @Mapper(componentModel = "default")
    interface Converter {
        fun toQry(request: Request): VideoCommentPageQry.Request

        @Mapping(source = "parentCommentId", target = "PCommentId")
        @Mapping(source = "videoId", target = "videoId")
        @Mapping(source = "videoName", target = "videoName")
        @Mapping(source = "videoCover", target = "videoCover")
        @Mapping(source = "customerId", target = "userId")
        @Mapping(source = "customerNickname", target = "nickName")
        @Mapping(source = "customerAvatar", target = "avatar")
        @Mapping(source = "replyCustomerId", target = "replyUserId")
        @Mapping(source = "replyCustomerNickname", target = "replyNickName")
        fun fromApp(resp: VideoCommentPageQry.Response): Response

        companion object {
            val INSTANCE: Converter = Mappers.getMapper(Converter::class.java)
        }
    }
}
