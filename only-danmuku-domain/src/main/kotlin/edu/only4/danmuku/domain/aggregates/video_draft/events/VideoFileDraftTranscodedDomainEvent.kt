package edu.only4.danmuku.domain.aggregates.video_draft.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent
import edu.only4.danmuku.domain.aggregates.video_draft.VideoFileDraft

/**
 * 视频文件草稿转码完成（成功或失败）
 */
@DomainEvent(persist = true)
@Aggregate(
    aggregate = "VideoDraft",
    name = "VideoFileDraftTranscodedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
data class VideoFileDraftTranscodedDomainEvent(
    val entity: VideoFileDraft,
    val success: Boolean,
    val errorMessage: String? = null,
)
