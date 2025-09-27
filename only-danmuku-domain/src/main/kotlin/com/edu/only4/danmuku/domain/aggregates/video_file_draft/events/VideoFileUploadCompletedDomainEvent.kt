package com.edu.only4.danmuku.domain.aggregates.video_file_draft.events

import com.edu.only4.danmuku.domain.aggregates.video_file_draft.VideoFileDraft
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

@DomainEvent(persist = false)
@Aggregate(aggregate = "VideoFileDraft", name = "VideoFileUploadCompletedDomainEvent", type = Aggregate.TYPE_DOMAIN_EVENT, description = "")
class VideoFileUploadCompletedDomainEvent(val entity: VideoFileDraft)
