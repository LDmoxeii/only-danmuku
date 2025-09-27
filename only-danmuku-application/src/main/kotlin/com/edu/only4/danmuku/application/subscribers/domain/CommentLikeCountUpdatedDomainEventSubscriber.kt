package com.edu.only4.danmuku.application.subscribers.domain

import com.edu.only4.danmuku.domain.aggregates.video_comment.events.CommentLikeCountUpdatedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class CommentLikeCountUpdatedDomainEventSubscriber {

    @EventListener(CommentLikeCountUpdatedDomainEvent::class)
    fun on(event: CommentLikeCountUpdatedDomainEvent) {

    }
}
