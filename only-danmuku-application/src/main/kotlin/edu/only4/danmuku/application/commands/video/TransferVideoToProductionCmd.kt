package edu.only4.danmuku.application.commands.video

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.domain._share.meta.video.SVideo
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import edu.only4.danmuku.domain.aggregates.video.Video
import edu.only4.danmuku.domain.aggregates.video.factory.VideoFactory
import edu.only4.danmuku.domain.aggregates.video_post.VideoFilePost
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

object TransferVideoToProductionCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val post = Mediator.repositories.findOne(
                SVideoPost.predicateById(request.videoPostId)
            ).getOrNull() ?: throw KnownException("稿件不存在: ${request.videoPostId}")
            if (post.videoFilePosts.isEmpty()) {
                throw KnownException("稿件文件不存在: ${request.videoPostId}")
            }
            val files = post.videoFilePosts.sortedBy { it.fileIndex }.map { file ->
                Video.SyncFileArgs(
                    videoFilePostId = file.id,
                    customerId = file.customerId,
                    fileName = file.fileName,
                    fileIndex = file.fileIndex,
                    fileSize = file.fileSize,
                    filePath = resolveFilePath(file),
                    duration = file.duration,
                )
            }

            val targetVideo = Mediator.repositories.findOne(
                SVideo.predicate { schema -> schema.videoPostId eq request.videoPostId }
            ).getOrNull()?.apply {
                syncFromBasics(
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
                    files = files
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
                    files = files
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
    ) : RequestParam<Response>

    data class Response(
        val videoId: Long,
    )

    private fun resolveFilePath(file: VideoFilePost): String? {
        return when {
            !file.encryptOutputPrefix.isNullOrBlank() -> file.encryptOutputPrefix
            !file.transcodeOutputPrefix.isNullOrBlank() -> file.transcodeOutputPrefix
            else -> null
        }
    }
}
