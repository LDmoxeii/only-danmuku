package com.edu.only4.danmuku.domain.aggregates.video_play_history.events

import com.edu.only4.danmuku.domain.aggregates.video_play_history.VideoPlayHistory
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

@DomainEvent(persist = false)
@Aggregate(aggregate = "VideoPlayHistory", name = "PlayHistoryBatchAddedDomainEvent", type = Aggregate.TYPE_DOMAIN_EVENT, description = "")
class PlayHistoryBatchAddedDomainEvent(val entity: VideoPlayHistory)
