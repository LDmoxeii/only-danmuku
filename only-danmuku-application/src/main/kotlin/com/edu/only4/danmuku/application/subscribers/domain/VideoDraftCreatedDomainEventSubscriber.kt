package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.video_draft.events.VideoDraftCreatedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class VideoDraftCreatedDomainEventSubscriber {

    @EventListener(VideoDraftCreatedDomainEvent::class)
    fun on(event: VideoDraftCreatedDomainEvent) {

    }
}
