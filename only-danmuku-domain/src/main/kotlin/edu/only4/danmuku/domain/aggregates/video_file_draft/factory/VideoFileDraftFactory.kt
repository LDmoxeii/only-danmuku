package edu.only4.danmuku.domain.aggregates.video_file_draft.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import edu.only4.danmuku.domain.aggregates.video_file_draft.VideoFileDraft
import edu.only4.danmuku.domain.aggregates.video_file_draft.enums.TransferResult
import edu.only4.danmuku.domain.aggregates.video_file_draft.enums.UpdateType
import org.springframework.stereotype.Service

/**
 * 视频文件信息;
 */
@Service
@Aggregate(
    aggregate = "VideoFileDraft",
    name = "VideoFileDraftFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class VideoFileDraftFactory : AggregateFactory<VideoFileDraftFactory.Payload, VideoFileDraft> {

    override fun create(payload: Payload): VideoFileDraft {
        return VideoFileDraft(
            id = 0L,
            fileId = payload.fileId,
            uploadId = payload.uploadId,
            customerId = payload.customerId,
            videoId = payload.videoId,
            fileIndex = payload.fileIndex,
            fileName = payload.fileName,
            fileSize = payload.fileSize,
            filePath = payload.filePath,
            updateType = payload.updateType ?: UpdateType.HAS_UPDATE,
            transferResult = payload.transferResult ?: TransferResult.TRANSCODING,
            duration = payload.duration,
            createUserId = null,
            createBy = null,
            createTime = null,
            updateUserId = null,
            updateBy = null,
            updateTime = null,
            deleted = 0L,
        )
    }

    @Aggregate(
        aggregate = "VideoFileDraft",
        name = "VideoFileDraftPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val fileId: Long,
        val uploadId: Long,
        val customerId: Long,
        val videoId: Long,
        val fileIndex: Int,
        val fileName: String? = null,
        val fileSize: Long? = null,
        val filePath: String? = null,
        val updateType: UpdateType? = null,
        val transferResult: TransferResult? = null,
        val duration: Int? = null,
    ) : AggregatePayload<VideoFileDraft>
}
