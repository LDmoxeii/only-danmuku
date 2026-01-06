package edu.only4.danmuku.domain.aggregates.video_post_processing.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.video_post_processing.VideoPostProcessing


/**
 * 处理聚合完成事件，驱动稿件状态与稿件文件/分辨率回填
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "VideoPostProcessing",
    name = "VideoPostProcessingCompletedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class VideoPostProcessingCompletedDomainEvent(
    val videoPostId: Long,
    val duration: Int?,
    val failedCount: Int,
    val lastFailReason: String?,
    val entity: VideoPostProcessing,
)
