package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.video_comment.events.CommentDeletedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class CommentDeletedDomainEventSubscriber {

    @EventListener(CommentDeletedDomainEvent::class)
    fun on(event: CommentDeletedDomainEvent) {

    }
}
