package edu.only4.danmuku.domain.aggregates.video_post_processing.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.video_post_processing.VideoPostProcessing


/**
 * 单个分P转码完成事件，携带 outputPrefix/encOutputDir/variantsJson，驱动加密编排
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "VideoPostProcessing",
    name = "VideoPostProcessingTranscodeCompletedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class VideoPostProcessingTranscodeCompletedDomainEvent(
    val entity: VideoPostProcessing
)
