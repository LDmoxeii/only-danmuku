package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.video_file_draft.events.VideoFileTransferFailedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class VideoFileTransferFailedDomainEventSubscriber {

    @EventListener(VideoFileTransferFailedDomainEvent::class)
    fun on(event: VideoFileTransferFailedDomainEvent) {

    }
}
