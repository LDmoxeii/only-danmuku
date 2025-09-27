package com.edu.only4.danmuku.domain.aggregates.video_danmuku.events

import com.edu.only4.danmuku.domain.aggregates.video_danmuku.VideoDanmuku
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

@DomainEvent(persist = false)
@Aggregate(aggregate = "VideoDanmuku", name = "DanmukuPostedDomainEvent", type = Aggregate.TYPE_DOMAIN_EVENT, description = "")
class DanmukuPostedDomainEvent(val entity: VideoDanmuku)
