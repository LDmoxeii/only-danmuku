package edu.only4.danmuku.domain.aggregates.video_post.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent
import edu.only4.danmuku.domain.aggregates.video_post.VideoPost

/**
 * 视频稿件需要进行转码（例如新增了分P）
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "VideoPost",
    name = "VideoPostTranscodingRequiredDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = "当视频稿件新增了分P等导致需要转码时触发"
)
class VideoPostTranscodingRequiredDomainEvent(val entity: VideoPost)

