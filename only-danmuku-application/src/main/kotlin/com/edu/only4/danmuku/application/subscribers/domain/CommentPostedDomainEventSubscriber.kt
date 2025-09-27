package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.video_comment.events.CommentPostedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class CommentPostedDomainEventSubscriber {

    @EventListener(CommentPostedDomainEvent::class)
    fun on(event: CommentPostedDomainEvent) {

    }
}
