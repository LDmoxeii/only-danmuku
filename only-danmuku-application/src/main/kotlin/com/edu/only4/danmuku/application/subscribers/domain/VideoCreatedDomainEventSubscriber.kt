package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.video.events.VideoCreatedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class VideoCreatedDomainEventSubscriber {

    @EventListener(VideoCreatedDomainEvent::class)
    fun on(event: VideoCreatedDomainEvent) {

    }
}
