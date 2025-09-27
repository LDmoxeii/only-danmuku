package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.video_comment.events.CommentRepliedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class CommentRepliedDomainEventSubscriber {

    @EventListener(CommentRepliedDomainEvent::class)
    fun on(event: CommentRepliedDomainEvent) {

    }
}
