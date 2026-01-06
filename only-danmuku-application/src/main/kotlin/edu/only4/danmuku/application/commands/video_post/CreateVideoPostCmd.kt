package edu.only4.danmuku.application.commands.video_post

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validator.MaxVideoPCount
import edu.only4.danmuku.domain.aggregates.video_post.VideoFilePost
import edu.only4.danmuku.domain.aggregates.video_post.VideoPost
import edu.only4.danmuku.domain.aggregates.video_post.enums.PostType
import edu.only4.danmuku.domain.aggregates.video_post.enums.TransferResult
import edu.only4.danmuku.domain.aggregates.video_post.factory.VideoPostFactory
import org.springframework.stereotype.Service

object CreateVideoPostCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val post = Mediator.factories.create(
                VideoPostFactory.Payload(
                    customerId = request.customerId,
                    videoName = request.videoName,
                    videoCover = request.videoCover,
                    pCategoryId = request.parentCategoryId,
                    categoryId = request.categoryId,
                    postType = request.postType,
                    originInfo = request.originInfo,
                    tags = request.tags,
                    introduction = request.introduction,
                    interaction = request.interaction,
                )
            )
            val filePosts = request.uploadFileList.map { spec ->
                VideoFilePost(
                    uploadId = spec.uploadId,
                    customerId = request.customerId,
                    fileIndex = spec.fileIndex,
                    fileName = spec.fileName,
                    fileSize = spec.fileSize,
                    duration = spec.duration,
                    transferResult = TransferResult.TRANSCODING
                )
            }
            post.videoFilePosts.addAll(filePosts)
            val transcodeFiles = request.uploadFileList.map { spec ->
                VideoPost.TranscodeFileSpec(
                    uploadId = spec.uploadId,
                    fileIndex = spec.fileIndex,
                    fileName = spec.fileName,
                    fileSize = spec.fileSize,
                    duration = spec.duration
                )
            }
            post.markTranscoding(transcodeFiles)

            Mediator.uow.save()

            return Response(videoId = post.id)
        }
    }

    @MaxVideoPCount(countField = "uploadFileList", videoIdField = "videoId")
    data class Request(
        val customerId: Long,
        val videoName: String,
        val videoCover: String? = null,
        val parentCategoryId: Long,
        val categoryId: Long? = null,
        val postType: PostType = PostType.valueOf(1),
        val originInfo: String? = null,
        val tags: String? = null,
        val introduction: String? = null,
        val interaction: String?,
        val uploadFileList: List<VideoPostFileSpec>,
    ) : RequestParam<Response>

    data class Response(
        val videoId: Long,
    )

    data class VideoPostFileSpec(
        val uploadId: Long,
        val fileIndex: Int,
        val fileName: String,
        val fileSize: Long?,
        val duration: Int?,
    )
}
