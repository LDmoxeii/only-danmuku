package edu.only4.danmuku.domain.aggregates.video_file_post.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent
import com.only4.cap4k.ddd.core.share.annotation.Retry

import edu.only4.danmuku.domain.aggregates.video_file_post.VideoFilePost


/**
 * 文件分P创建完成事件，驱动转码 CLI
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@Retry(retryIntervals = [10, 20, 30])
@DomainEvent(persist = true)
@Aggregate(
    aggregate = "VideoFilePost",
    name = "VideoFilePostCreatedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class VideoFilePostCreatedDomainEvent(val entity: VideoFilePost)
