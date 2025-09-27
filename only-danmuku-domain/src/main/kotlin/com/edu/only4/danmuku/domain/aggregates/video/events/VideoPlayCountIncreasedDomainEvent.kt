package com.edu.only4.danmuku.domain.aggregates.video.events

import com.edu.only4.danmuku.domain.aggregates.video.Video
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

@DomainEvent(persist = false)
@Aggregate(aggregate = "Video", name = "VideoPlayCountIncreasedDomainEvent", type = Aggregate.TYPE_DOMAIN_EVENT, description = "")
class VideoPlayCountIncreasedDomainEvent(val entity: Video)
