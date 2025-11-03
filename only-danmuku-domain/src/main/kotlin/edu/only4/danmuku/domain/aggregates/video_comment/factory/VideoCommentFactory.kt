package edu.only4.danmuku.domain.aggregates.video_comment.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video_comment.VideoComment

import org.springframework.stereotype.Service

/**
 * 评论;
 */
@Service
@Aggregate(
    aggregate = "VideoComment",
    name = "VideoCommentFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class VideoCommentFactory : AggregateFactory<VideoCommentFactory.Payload, VideoComment> {

    override fun create(payload: Payload): VideoComment {
        return VideoComment(
            id = 0L,
            parentId = payload.parentId,
            videoId = payload.videoId,
            videoOwnerId = payload.videoOwnerId,
            content = payload.content,
            imgPath = payload.imgPath,
            customerId = payload.customerId,
            replyCustomerId = payload.replyCustomerId,
            topType = 0,
            postTime = payload.postTime,
            likeCount = 0,
            hateCount = 0,
        )
    }

     @Aggregate(
        aggregate = "VideoComment",
        name = "VideoCommentPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
         val parentId: Long = 0L,
         val videoId: Long,
         val videoOwnerId: Long,
         val content: String,
         val imgPath: String?,
         val customerId: Long,
         val replyCustomerId: Long?,
         val postTime: Long,
    ) : AggregatePayload<VideoComment>

}
