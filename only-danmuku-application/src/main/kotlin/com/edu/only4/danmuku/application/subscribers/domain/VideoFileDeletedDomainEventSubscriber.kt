package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.video_file.events.VideoFileDeletedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class VideoFileDeletedDomainEventSubscriber {

    @EventListener(VideoFileDeletedDomainEvent::class)
    fun on(event: VideoFileDeletedDomainEvent) {

    }
}
