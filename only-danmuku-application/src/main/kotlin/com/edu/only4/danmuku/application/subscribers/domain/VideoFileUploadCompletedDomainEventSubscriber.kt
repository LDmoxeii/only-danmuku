package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.video_file_draft.events.VideoFileUploadCompletedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class VideoFileUploadCompletedDomainEventSubscriber {

    @EventListener(VideoFileUploadCompletedDomainEvent::class)
    fun on(event: VideoFileUploadCompletedDomainEvent) {

    }
}
