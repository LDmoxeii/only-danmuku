package edu.only4.danmuku.domain.aggregates.video_post_processing.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent
import edu.only4.danmuku.domain.aggregates.video_post_processing.VideoPostProcessing

/**
 * 单个分P加密上下文准备完成事件
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/06
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "VideoPostProcessing",
    name = "VideoPostProcessingEncryptContextPreparedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class VideoPostProcessingEncryptContextPreparedDomainEvent(
    val videoPostId: Long,
    val fileIndex: Int,
    val keyVersion: Int,
    val transcodeOutputPrefix: String?,
    val encryptOutputDir: String?,
    val variantsJson: String?,
    val entity: VideoPostProcessing,
)
