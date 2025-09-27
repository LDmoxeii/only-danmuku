package com.edu.only4.danmuku.domain.aggregates.video_draft.events

import com.edu.only4.danmuku.domain.aggregates.video_draft.VideoDraft
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

@DomainEvent(persist = false)
@Aggregate(aggregate = "VideoDraft", name = "VideoTransferredToProductionDomainEvent", type = Aggregate.TYPE_DOMAIN_EVENT, description = "")
class VideoTransferredToProductionDomainEvent(val entity: VideoDraft)
