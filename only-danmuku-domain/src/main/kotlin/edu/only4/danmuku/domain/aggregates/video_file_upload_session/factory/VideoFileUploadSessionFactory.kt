package edu.only4.danmuku.domain.aggregates.video_file_upload_session.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video_file_upload_session.VideoFileUploadSession
import edu.only4.danmuku.domain.aggregates.video_file_upload_session.enums.UploadStatus

import org.springframework.stereotype.Service

/**
 * 视频分片上传会话; 用于跟踪预上传与分片进度
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/28
 */
@Service
@Aggregate(
    aggregate = "VideoFileUploadSession",
    name = "VideoFileUploadSessionFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class VideoFileUploadSessionFactory : AggregateFactory<VideoFileUploadSessionFactory.Payload, VideoFileUploadSession> {

    override fun create(payload: Payload): VideoFileUploadSession {
        return VideoFileUploadSession(
            customerId = payload.customerId,
            fileName = payload.fileName,
            chunks = payload.chunks,
            chunkIndex = 0,
            fileSize = 0,
            status = UploadStatus.CREATED,
            expiresAt = payload.expiresAt,
        )
    }

    @Aggregate(
        aggregate = "VideoFileUploadSession",
        name = "VideoFileUploadSessionPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val customerId: Long,
        val fileName: String,
        val chunks: Int,
        val expiresAt: Long,
    ) : AggregatePayload<VideoFileUploadSession>

}
