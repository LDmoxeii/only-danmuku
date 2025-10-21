package edu.only4.danmuku.domain.aggregates.video_play_history.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.video_play_history.VideoPlayHistory


/**
 * 播放进度已更新
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@DomainEvent(persist = true)
@Aggregate(
    aggregate = "VideoPlayHistory",
    name = "PlayProgressUpdatedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class PlayProgressUpdatedDomainEvent(val entity: VideoPlayHistory)
