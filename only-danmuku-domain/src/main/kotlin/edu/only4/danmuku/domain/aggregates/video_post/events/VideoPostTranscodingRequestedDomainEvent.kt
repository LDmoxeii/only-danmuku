package edu.only4.danmuku.domain.aggregates.video_post.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent
import edu.only4.danmuku.domain.aggregates.video_post.VideoPost

/**
 * 视频稿件发起转码处理
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "VideoPost",
    name = "VideoPostTranscodingRequestedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class VideoPostTranscodingRequestedDomainEvent(
    val videoPostId: Long,
    val fileList: List<FileItem>,
    val entity: VideoPost,
) {
    data class FileItem(
        val uploadId: Long,
        val fileIndex: Int,
        val fileName: String?,
        val fileSize: Long?,
        val duration: Int?
    )
}
