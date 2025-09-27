package com.edu.only4.danmuku.domain.aggregates.video_comment.events

import com.edu.only4.danmuku.domain.aggregates.video_comment.VideoComment
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

@DomainEvent(persist = false)
@Aggregate(aggregate = "VideoComment", name = "CommentRepliedDomainEvent", type = Aggregate.TYPE_DOMAIN_EVENT, description = "")
class CommentRepliedDomainEvent(val entity: VideoComment)
