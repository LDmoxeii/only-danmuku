package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.video_file_draft.events.VideoFileTransferCompletedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class VideoFileTransferCompletedDomainEventSubscriber {

    @EventListener(VideoFileTransferCompletedDomainEvent::class)
    fun on(event: VideoFileTransferCompletedDomainEvent) {

    }
}
