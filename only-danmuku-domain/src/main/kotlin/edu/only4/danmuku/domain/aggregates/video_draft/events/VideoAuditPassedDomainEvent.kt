package edu.only4.danmuku.domain.aggregates.video_draft.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.video_post.VideoPost


/**
 * 视频审核通过事件订阅（触发发送系统消息）
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/30
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "VideoDraft",
    name = "VideoAuditPassedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class VideoAuditPassedDomainEvent(val entity: VideoPost)
