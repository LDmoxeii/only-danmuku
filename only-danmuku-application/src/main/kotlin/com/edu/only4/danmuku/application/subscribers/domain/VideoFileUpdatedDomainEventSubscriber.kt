package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.video_file.events.VideoFileUpdatedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class VideoFileUpdatedDomainEventSubscriber {

    @EventListener(VideoFileUpdatedDomainEvent::class)
    fun on(event: VideoFileUpdatedDomainEvent) {

    }
}
