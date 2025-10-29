package edu.only4.danmuku.application.commands.video_draft

import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.MaxVideoPCount
import edu.only4.danmuku.domain.aggregates.video.enums.PostType
import edu.only4.danmuku.domain.aggregates.video_post.VideoPost
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
                    customerId = request.customerId,
                    videoName = request.videoName,
                    videoCover = request.videoCover,
                    pCategoryId = request.parentCategoryId,
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
                    VideoPost.UploadSpec(
                        uploadId = file.uploadId,
                        fileIndex = file.fileIndex,
                        fileName = file.fileName,
                    )
                }

                draft.initializeFilesFromUploads(
                    customerId = request.customerId,
                    uploads = uploadSpecs
                )
            }

            Mediator.uow.save()

            return Response(videoId = draft.id)
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
        val uploadFileList: List<VideoFileInfo> = emptyList(),
    ) : RequestParam<Response>

    data class VideoFileInfo(
        val uploadId: Long,
        val fileIndex: Int,
        val fileName: String,
    )

    data class Response(
        val videoId: Long,
    )
}
