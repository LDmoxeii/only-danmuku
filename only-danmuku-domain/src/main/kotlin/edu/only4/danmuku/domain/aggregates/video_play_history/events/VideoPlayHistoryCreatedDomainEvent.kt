package edu.only4.danmuku.domain.aggregates.video_play_history.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.video_play_history.VideoPlayHistory


/**
 * 播放历史记录已创建
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/19
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "VideoPlayHistory",
    name = "VideoPlayHistoryCreatedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class VideoPlayHistoryCreatedDomainEvent(val entity: VideoPlayHistory)
