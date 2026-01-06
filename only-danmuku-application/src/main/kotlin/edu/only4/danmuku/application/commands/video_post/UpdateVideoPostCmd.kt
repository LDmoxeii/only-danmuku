package edu.only4.danmuku.application.commands.video_post

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validator.VideoPostExists
import edu.only4.danmuku.application.validator.VideoPostEditableStatus
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import edu.only4.danmuku.domain.aggregates.video_post.VideoFilePost
import edu.only4.danmuku.domain.aggregates.video_post.VideoPost
import edu.only4.danmuku.domain.aggregates.video_post.enums.PostType
import edu.only4.danmuku.domain.aggregates.video_post.enums.TransferResult
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

object UpdateVideoPostCmd {

    @Service
    class Handler : Command<Request, Response> {
        override fun exec(request: Request): Response {
            val post = Mediator.repositories.findFirst(
                SVideoPost.predicate { it.id eq request.videoPostId },
            ).getOrNull() ?: throw KnownException("视频草稿不存在: ${request.videoPostId}")

            if (post.customerId != request.customerId) {
                throw KnownException("无权编辑该视频草稿")
            }

            // 将实体加入工作单元，确保在 save() 时可被 merge/flush/refresh
            Mediator.uow.persist(post)

            val basicChanged = post.applyBasicInfo(
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

            val incomingFiles = request.uploadFileList
            val transcodeFiles = mutableListOf<VideoPost.TranscodeFileSpec>()
            if (incomingFiles.isNotEmpty()) {
                val seenIndex = mutableSetOf<Int>()
                val existingByIndex = post.videoFilePosts.associateBy { it.fileIndex }
                incomingFiles.forEach { spec ->
                    if (!seenIndex.add(spec.fileIndex)) {
                        throw KnownException("文件索引重复: ${spec.fileIndex}")
                    }
                    val existing = existingByIndex[spec.fileIndex]
                    if (existing == null || existing.uploadId != spec.uploadId) {
                        if (existing != null) {
                            post.videoFilePosts.remove(existing)
                        }
                        post.videoFilePosts.add(
                            VideoFilePost(
                                uploadId = spec.uploadId,
                                customerId = request.customerId,
                                fileIndex = spec.fileIndex,
                                fileName = spec.fileName,
                                fileSize = spec.fileSize,
                                duration = spec.duration,
                                transferResult = TransferResult.TRANSCODING
                            )
                        )
                        transcodeFiles.add(
                            VideoPost.TranscodeFileSpec(
                                uploadId = spec.uploadId,
                                fileIndex = spec.fileIndex,
                                fileName = spec.fileName,
                                fileSize = spec.fileSize,
                                duration = spec.duration
                            )
                        )
                    }
                }
            }
            if (transcodeFiles.isNotEmpty()) {
                post.markTranscoding(transcodeFiles)
            } else if (basicChanged) {
                post.markPendingReview()
            }

            Mediator.uow.save()
            return Response(videoId = post.id)
        }
    }

    @VideoPostExists(videoIdField = "videoPostId")
    @VideoPostEditableStatus(videoIdField = "videoPostId")
    data class Request(
        val videoPostId: Long,
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
        val uploadFileList: List<VideoPostFileSpec> = emptyList(),
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
