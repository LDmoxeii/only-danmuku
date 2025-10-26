package edu.only4.danmuku.domain.aggregates.video_post.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent
import edu.only4.danmuku.domain.aggregates.video_post.VideoFilePost

/**
 * 视频文件草稿已创建
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "VideoDraft",
    name = "VideoFileDraftCreatedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class VideoFileDraftCreatedDomainEvent(val entity: VideoFilePost)
