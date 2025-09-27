package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.video_draft.events.VideoTransferredToProductionDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class VideoTransferredToProductionDomainEventSubscriber {

    @EventListener(VideoTransferredToProductionDomainEvent::class)
    fun on(event: VideoTransferredToProductionDomainEvent) {

    }
}
