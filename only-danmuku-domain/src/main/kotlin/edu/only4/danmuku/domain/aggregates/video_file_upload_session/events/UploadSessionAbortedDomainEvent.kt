package edu.only4.danmuku.domain.aggregates.video_file_upload_session.events

import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import com.only4.cap4k.ddd.core.domain.event.annotation.DomainEvent

import edu.only4.danmuku.domain.aggregates.video_file_upload_session.VideoFileUploadSession


/**
 * 视频文件上传会话已中止
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/19
 */
@DomainEvent(persist = false)
@Aggregate(
    aggregate = "VideoFileUploadSession",
    name = "VideoFileUploadSessionAbortedDomainEvent",
    type = Aggregate.TYPE_DOMAIN_EVENT,
    description = ""
)
class UploadSessionAbortedDomainEvent(val entity: VideoFileUploadSession)
