package edu.only4.danmuku.application.commands.video_draft

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.MaxVideoPCount
import edu.only4.danmuku.domain.aggregates.video.enums.PostType
import edu.only4.danmuku.domain.aggregates.video_post.VideoFilePost
import edu.only4.danmuku.domain.aggregates.video_post.enums.VideoStatus
import edu.only4.danmuku.domain.aggregates.video_post.factory.VideoPostFactory
import org.springframework.stereotype.Service

/**
 * 创建视频草稿
 */
object CreateVideoDraftCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val draft = Mediator.factories.create(
                VideoPostFactory.Payload(
                    videoId = request.videoId,
                    customerId = request.customerId,
                    videoName = request.videoName,
                    videoCover = request.videoCover,
                    pCategoryId = request.pCategoryId,
                    categoryId = request.categoryId,
                    postType = request.postType,
                    originInfo = request.originInfo,
                    tags = request.tags,
                    introduction = request.introduction,
                    status = VideoStatus.TRANSCODING,
                )
            )

            if (request.uploadFileList.isNotEmpty()) {
                val uploadSpecs = request.uploadFileList.map { file ->
                    val uploadId = file.uploadId.toLongOrNull()
                        ?: throw KnownException("非法的 uploadId: ${file.uploadId}")
                    VideoFilePost.UploadSpec(
                        uploadId = uploadId,
                        fileIndex = file.fileIndex,
                        fileName = file.fileName,
                        fileSize = file.fileSize,
                        duration = file.duration,
                    )
                }

                val buildResult = runCatching {
                    VideoFilePost.buildFromUploads(
                        customerId = request.customerId,
                        videoPost = draft,
                        uploads = uploadSpecs
                    )
                }.getOrElse { ex ->
                    if (ex is IllegalArgumentException) {
                        throw KnownException(ex.message ?: "非法的上传文件参数")
                    }
                    throw ex
                }

                draft.videoFilePosts.clear()
                draft.videoFilePosts.addAll(buildResult.fileDrafts)
                draft.duration = buildResult.totalDuration.takeIf { it > 0 }
            }

            Mediator.uow.save()

            return Response(videoId = draft.id)
        }
    }

    @MaxVideoPCount(countField = "uploadFileList", videoIdField = "videoId")
    data class Request(
        val videoId: Long,
        val customerId: Long,
        val videoName: String,
        val videoCover: String? = null,
        val pCategoryId: Long,
        val categoryId: Long? = null,
        val postType: PostType = PostType.valueOf(1),
        val originInfo: String? = null,
        val tags: String? = null,
        val introduction: String? = null,
        val uploadFileList: List<VideoFileInfo> = emptyList(),
    ) : RequestParam<Response>

    data class VideoFileInfo(
        val uploadId: String,
        val fileIndex: Int,
        val fileName: String,
        val fileSize: Long,
        val duration: Int,
    )

    data class Response(
        val videoId: Long,
    )
}
