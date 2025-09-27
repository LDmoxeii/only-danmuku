package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.video.events.VideoDeletedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class VideoDeletedDomainEventSubscriber {

    @EventListener(VideoDeletedDomainEvent::class)
    fun on(event: VideoDeletedDomainEvent) {

    }
}
