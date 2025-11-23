package edu.only4.danmuku.domain.aggregates.video.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.video.Video


/**
 * 视频播放数变更
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/23
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "Video",
    name = "VideoPlayCountDeltaAppliedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class VideoPlayCountDeltaAppliedDomainEvent(val entity: Video, val delta: Int)
