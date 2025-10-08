package com.edu.only4.danmuku.domain.aggregates.video_comment.factory

import com.edu.only4.danmuku.domain.aggregates.video_comment.VideoComment
import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import org.springframework.stereotype.Service

@Service
@Aggregate(aggregate = "VideoComment", name = "VideoCommentFactory", type = Aggregate.TYPE_FACTORY, description = "")
class VideoCommentFactory : AggregateFactory<VideoCommentFactory.Payload, VideoComment> {
    override fun create(entityPayload: Payload): VideoComment {
        return VideoComment(

        )
    }

    @Aggregate(aggregate = "VideoComment", name = "VideoCommentPayload", type = Aggregate.TYPE_FACTORY_PAYLOAD, description = "")
    class Payload(

    ) : AggregatePayload<VideoComment>
}
