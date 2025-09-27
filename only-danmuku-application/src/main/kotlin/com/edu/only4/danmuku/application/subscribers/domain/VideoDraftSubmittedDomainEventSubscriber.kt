package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.video_draft.events.VideoDraftSubmittedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class VideoDraftSubmittedDomainEventSubscriber {

    @EventListener(VideoDraftSubmittedDomainEvent::class)
    fun on(event: VideoDraftSubmittedDomainEvent) {

    }
}
