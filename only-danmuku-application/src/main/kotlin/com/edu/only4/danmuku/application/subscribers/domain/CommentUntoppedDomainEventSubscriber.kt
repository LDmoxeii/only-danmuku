package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.video_comment.events.CommentUntoppedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class CommentUntoppedDomainEventSubscriber {

    @EventListener(CommentUntoppedDomainEvent::class)
    fun on(event: CommentUntoppedDomainEvent) {

    }
}
