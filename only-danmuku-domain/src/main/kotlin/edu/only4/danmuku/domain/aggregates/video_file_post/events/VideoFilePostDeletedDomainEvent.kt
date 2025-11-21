package edu.only4.danmuku.domain.aggregates.video_file_post.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.video_file_post.VideoFilePost


/**
 * 分P被删除事件，驱动稿件状态刷新
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "VideoFilePost",
    name = "VideoFilePostDeletedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class VideoFilePostDeletedDomainEvent(val entity: VideoFilePost)
