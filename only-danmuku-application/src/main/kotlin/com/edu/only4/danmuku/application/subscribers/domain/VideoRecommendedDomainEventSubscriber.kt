package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.video.events.VideoRecommendedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class VideoRecommendedDomainEventSubscriber {

    @EventListener(VideoRecommendedDomainEvent::class)
    fun on(event: VideoRecommendedDomainEvent) {

    }
}
