package edu.only4.danmuku.application.commands.video_file_draft

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.MaxVideoPCount
import edu.only4.danmuku.domain._share.meta.video_file_draft.SVideoFileDraft
import edu.only4.danmuku.domain.aggregates.video_file_draft.enums.TransferResult
import edu.only4.danmuku.domain.aggregates.video_file_draft.enums.UpdateType
import edu.only4.danmuku.domain.aggregates.video_file_draft.factory.VideoFileDraftFactory
import org.springframework.stereotype.Service

/**
 * 创建单个视频文件草稿（使用工厂）
 */
object CreateVideoFileDraftCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 计算下一个文件索引
            val existing = Mediator.repositories.find(
                SVideoFileDraft.predicate { it.videoId eq request.videoId }
            )
            val nextIndex = (existing.maxOfOrNull { it.fileIndex } ?: 0) + 1

            val fileId = generateFileId()

            // 使用工厂创建
            val draft = Mediator.factories.create(
                VideoFileDraftFactory.Payload(
                    fileId = fileId,
                    uploadId = request.uploadId,
                    customerId = request.customerId,
                    videoId = request.videoId,
                    fileIndex = nextIndex,
                    fileName = request.fileName,
                    updateType = UpdateType.HAS_UPDATE,
                    transferResult = TransferResult.TRANSCODING,
                )
            )

            Mediator.uow.save()
            return Response(fileId = draft.fileId, fileIndex = draft.fileIndex)
        }

        private fun generateFileId(): Long =
            System.currentTimeMillis() shl 8 or ((Math.random() * 255).toLong() and 0xFF)
    }

    @MaxVideoPCount(countField = "pCount")
    data class Request(
        val customerId: Long,
        val videoId: Long,
        val uploadId: Long,
        val fileName: String? = null,
        // 当前视频已有的分片数量（用于校验），如果未提供则由服务端自动统计
        val pCount: Int = 0,
    ) : RequestParam<Response>

    data class Response(
        val fileId: Long,
        val fileIndex: Int,
    )
}
