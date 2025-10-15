package edu.only4.danmuku.domain.aggregates.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.video_comment.VideoComment


/**
 * 评论已删除
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@DomainEvent(persist = true)
@Aggregate(
    aggregate = "VideoComment",
    name = "CommentDeletedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class CommentDeletedDomainEvent(val entity: VideoComment)
