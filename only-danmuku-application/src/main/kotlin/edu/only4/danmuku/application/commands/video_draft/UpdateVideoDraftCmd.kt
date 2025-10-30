package edu.only4.danmuku.application.commands.video_draft

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validator.MaxVideoPCount
import edu.only4.danmuku.application.validator.VideoDraftExists
import edu.only4.danmuku.application.validator.VideoEditableStatus
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import edu.only4.danmuku.domain.aggregates.video.enums.PostType
import edu.only4.danmuku.domain.aggregates.video_post.VideoPost
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * 更新视频草稿
 */
object UpdateVideoDraftCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val draft = Mediator.repositories.findFirst(
                SVideoPost.predicate { it.id eq request.videoId },
            ).getOrNull() ?: throw KnownException("视频草稿不存在: ${request.videoId}")

            if (draft.customerId != request.customerId) {
                throw KnownException("无权编辑该视频草稿")
            }

            val basicChanged = draft.applyBasicInfo(
                videoName = request.videoName,
                videoCover = request.videoCover,
                pCategoryId = request.pCategoryId,
                categoryId = request.categoryId,
                postType = request.postType,
                originInfo = request.originInfo,
                tags = request.tags,
                introduction = request.introduction,
                interaction = request.interaction,
            )

            var outcome: VideoPost.FileEditOutcome? = null
            request.uploadFileList?.let { uploadFileList ->
                if (uploadFileList.isEmpty()) {
                    outcome = draft.applyFileEdits(request.customerId, emptyList())
                } else {
                    val specs = uploadFileList.sortedBy { it.fileIndex }.map {
                        VideoPost.FileEditSpec(
                            fileId = it.fileId,
                            uploadId = it.uploadId,
                            fileIndex = it.fileIndex,
                            fileName = it.fileName,
                        )
                    }
                    outcome = draft.applyFileEdits(request.customerId, specs)
                }
            }

            draft.adjustStatusAfterEdit(basicChanged, outcome)

            Mediator.uow.save()
            return Response(videoId = draft.id)
        }
    }

    @VideoDraftExists
    @VideoEditableStatus
    @MaxVideoPCount(countField = "uploadFileList", videoIdField = "videoId")
    data class Request(
        val videoId: Long,
        val customerId: Long,
        val videoName: String? = null,
        val videoCover: String? = null,
        val pCategoryId: Long? = null,
        val categoryId: Long? = null,
        val postType: PostType? = null,
        val originInfo: String? = null,
        val tags: String? = null,
        val introduction: String? = null,
        val interaction: String? = null,
        val uploadFileList: List<VideoFileInfo>? = null,
    ) : RequestParam<Response>

    data class VideoFileInfo(
        val fileId: Long? = null,
        val uploadId: Long? = null,
        val fileIndex: Int,
        val fileName: String,
    )

    data class Response(
        val videoId: Long,
    )
}
