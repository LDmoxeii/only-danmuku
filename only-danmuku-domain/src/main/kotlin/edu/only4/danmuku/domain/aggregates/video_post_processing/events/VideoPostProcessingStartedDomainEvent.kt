package edu.only4.danmuku.domain.aggregates.video_post_processing.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.video_post_processing.VideoPostProcessing


/**
 * 处理聚合已初始化事件，携带 fileList(uploadId,fileIndex,outputDir,objectPrefix,encOutputDir)
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "VideoPostProcessing",
    name = "VideoPostProcessingStartedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class VideoPostProcessingStartedDomainEvent(
    val videoPostId: Long,
    val fileList: List<FileItem>,
    val entity: VideoPostProcessing,
) {
    data class FileItem(
        val uploadId: Long,
        val fileIndex: Int,
        val outputDir: String,
        val objectPrefix: String,
        val encOutputDir: String,
    )
}
