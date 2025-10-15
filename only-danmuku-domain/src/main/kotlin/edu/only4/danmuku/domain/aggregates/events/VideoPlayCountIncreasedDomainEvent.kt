package edu.only4.danmuku.domain.aggregates.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.video.Video


/**
 * 视频播放数已增加
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@DomainEvent(persist = true)
@Aggregate(
    aggregate = "Video",
    name = "VideoPlayCountIncreasedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class VideoPlayCountIncreasedDomainEvent(val entity: Video)
