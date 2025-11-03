package edu.only4.danmuku.application.commands.video

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video.SVideo
import edu.only4.danmuku.domain.aggregates.video.Video
import edu.only4.danmuku.domain.aggregates.video.factory.VideoFactory
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

object TransferVideoToProductionCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            // 若已存在成品视频，则走更新；否则新建后同步
            val targetVideo = Mediator.repositories.findOne(
                SVideo.predicate { schema -> schema.videoPostId eq request.videoPostId }
            ).getOrNull()?.apply {
                this.syncFromBasics(
                    videoPostId = request.videoPostId,
                    customerId = request.customerId,
                    videoCover = request.videoCover,
                    videoName = request.videoName,
                    parentCategoryId = request.parentCategoryId,
                    categoryId = request.categoryId,
                    postType = request.postType,
                    originInfo = request.originInfo,
                    tags = request.tags,
                    introduction = request.introduction,
                    interaction = request.interaction,
                    duration = request.duration,
                    files = request.files.map {
                        Video.SyncFileArgs(
                            videoFilePostId = it.videoFilePostId,
                            customerId = it.customerId,
                            fileName = it.fileName,
                            fileIndex = it.fileIndex,
                            fileSize = it.fileSize,
                            filePath = it.filePath,
                            duration = it.duration,
                        )
                    }
                )
            } ?: Mediator.factories.create(
                VideoFactory.Payload(
                    videoPostId = request.videoPostId,
                    customerId = request.customerId,
                    videoCover = request.videoCover,
                    videoName = request.videoName,
                    parentCategoryId = request.parentCategoryId,
                    categoryId = request.categoryId,
                    postType = request.postType,
                    originInfo = request.originInfo,
                    tags = request.tags,
                    introduction = request.introduction,
                    interaction = request.interaction,
                    duration = request.duration,
                    files = request.files.map {
                        Video.SyncFileArgs(
                            videoFilePostId = it.videoFilePostId,
                            customerId = it.customerId,
                            fileName = it.fileName,
                            fileIndex = it.fileIndex,
                            fileSize = it.fileSize,
                            filePath = it.filePath,
                            duration = it.duration,
                        )
                    }
                )
            )
            // 持久化变更/创建
            Mediator.uow.save()

            return Response(videoId = targetVideo.id)
        }
    }

    data class Request(
        val videoPostId: Long,
        val customerId: Long,
        val videoCover: String,
        val videoName: String,
        val parentCategoryId: Long,
        val categoryId: Long?,
        val postType: Int,
        val originInfo: String?,
        val tags: String?,
        val introduction: String?,
        val interaction: String?,
        val duration: Int,
        val files: List<FileItem>,
    ) : RequestParam<Response>

    data class FileItem(
        val videoFilePostId: Long,
        val customerId: Long,
        val fileName: String?,
        val fileIndex: Int,
        val fileSize: Long?,
        val filePath: String?,
        val duration: Int?,
    )

    data class Response(
        val videoId: Long,
    )
}
