package com.edu.only4.danmuku.domain.aggregates.video_file.events

import com.edu.only4.danmuku.domain.aggregates.video_file.VideoFile
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

@DomainEvent(persist = false)
@Aggregate(aggregate = "VideoFile", name = "VideoFileDeletedDomainEvent", type = Aggregate.TYPE_DOMAIN_EVENT, description = "")
class VideoFileDeletedDomainEvent(val entity: VideoFile)
