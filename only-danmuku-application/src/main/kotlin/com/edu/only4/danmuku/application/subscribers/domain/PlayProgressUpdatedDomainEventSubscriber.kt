package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.video_play_history.events.PlayProgressUpdatedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class PlayProgressUpdatedDomainEventSubscriber {

    @EventListener(PlayProgressUpdatedDomainEvent::class)
    fun on(event: PlayProgressUpdatedDomainEvent) {

    }
}
