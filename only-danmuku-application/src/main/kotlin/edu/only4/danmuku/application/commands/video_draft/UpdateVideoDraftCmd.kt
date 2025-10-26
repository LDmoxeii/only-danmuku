package edu.only4.danmuku.application.commands.video_draft

import com.only.engine.exception.KnownException
import com.only4.cap4k.ddd.core.Mediator
import com.only4.cap4k.ddd.core.application.RequestParam
import com.only4.cap4k.ddd.core.application.command.Command
import edu.only4.danmuku.application.validater.MaxVideoPCount
import edu.only4.danmuku.application.validater.VideoDraftExists
import edu.only4.danmuku.application.validater.VideoEditableStatus
import edu.only4.danmuku.domain._share.meta.video_post.SVideoPost
import edu.only4.danmuku.domain.aggregates.video.enums.PostType
import edu.only4.danmuku.domain.aggregates.video_post.VideoFilePost
import edu.only4.danmuku.domain.aggregates.video_post.enums.TransferResult
import edu.only4.danmuku.domain.aggregates.video_post.enums.UpdateType
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
                persist = false
            ).getOrNull() ?: throw KnownException("视频草稿不存在: ${request.videoId}")

            if (draft.customerId != request.customerId) {
                throw KnownException("无权编辑该视频草稿")
            }

            val originalVideoName = draft.videoName
            val originalVideoCover = draft.videoCover
            val originalPCategoryId = draft.pCategoryId
            val originalCategoryId = draft.categoryId
            val originalPostType = draft.postType
            val originalOriginInfo = draft.originInfo
            val originalTags = draft.tags
            val originalIntroduction = draft.introduction
            val originalDuration = draft.duration

            request.videoName?.let { draft.videoName = it }
            request.videoCover?.let { draft.videoCover = it }
            request.pCategoryId?.let { draft.pCategoryId = it }
            draft.categoryId = request.categoryId
            draft.postType = request.postType ?: draft.postType
            draft.originInfo = request.originInfo
            draft.tags = request.tags
            draft.introduction = request.introduction

            var hasNewFiles = false
            var hasRemovedFiles = false
            var hasFileMetaChange = false

            request.uploadFileList?.let { uploadFileList ->
                if (uploadFileList.isEmpty()) {
                    if (draft.videoFilePosts.isNotEmpty()) {
                        hasRemovedFiles = true
                        draft.videoFilePosts.clear()
                        draft.duration = null
                    }
                    return@let
                }

                val existingFileMap = draft.videoFilePosts.associateBy { it.uploadId }.toMutableMap()
                val seenUploadIds = mutableSetOf<Long>()
                val sortedUploads = uploadFileList.sortedBy { it.fileIndex }

                val rebuiltList = mutableListOf<VideoFilePost>()
                sortedUploads.forEachIndexed { index, fileInfo ->
                    val uploadId = fileInfo.uploadId.toLongOrNull()
                        ?: throw KnownException("非法的 uploadId: ${fileInfo.uploadId}")
                    if (!seenUploadIds.add(uploadId)) {
                        throw KnownException("重复的 uploadId: ${fileInfo.uploadId}")
                    }

                    val normalizedIndex = index + 1
                    val existingFile = existingFileMap.remove(uploadId)
                    if (existingFile != null) {
                        if (existingFile.fileIndex != normalizedIndex) {
                            existingFile.fileIndex = normalizedIndex
                            hasFileMetaChange = true
                        }
                        if (fileInfo.fileName != existingFile.fileName) {
                            existingFile.fileName = fileInfo.fileName
                            hasFileMetaChange = true
                        }
                        if (fileInfo.fileSize != existingFile.fileSize) {
                            existingFile.fileSize = fileInfo.fileSize
                            hasFileMetaChange = true
                        }
                        val currentDuration = existingFile.duration ?: 0
                        if (fileInfo.duration != currentDuration) {
                            existingFile.duration = fileInfo.duration
                            hasFileMetaChange = true
                        }
                        rebuiltList.add(existingFile)
                    } else {
                        hasNewFiles = true
                        val newFile = VideoFilePost(
                            uploadId = uploadId,
                            customerId = request.customerId,
                            fileIndex = normalizedIndex,
                            fileName = fileInfo.fileName,
                            fileSize = fileInfo.fileSize,
                            updateType = UpdateType.HAS_UPDATE,
                            transferResult = TransferResult.TRANSCODING,
                            duration = fileInfo.duration
                        )
                        rebuiltList.add(newFile)
                    }
                }

                if (existingFileMap.isNotEmpty()) {
                    hasRemovedFiles = true
                }

                draft.videoFilePosts.clear()
                draft.videoFilePosts.addAll(rebuiltList)

                val totalDuration = sortedUploads.sumOf { it.duration }
                val normalizedDuration = totalDuration.takeIf { it > 0 }
                if (normalizedDuration != draft.duration) {
                    draft.duration = normalizedDuration
                    hasFileMetaChange = true
                }
            }

            val basicInfoChanged =
                draft.videoName != originalVideoName ||
                        draft.videoCover != originalVideoCover ||
                        draft.pCategoryId != originalPCategoryId ||
                        draft.categoryId != originalCategoryId ||
                        draft.postType != originalPostType ||
                        draft.originInfo != originalOriginInfo ||
                        draft.tags != originalTags ||
                        draft.introduction != originalIntroduction ||
                        draft.duration != originalDuration

            when {
                hasNewFiles -> draft.markTranscoding()
                basicInfoChanged || hasFileMetaChange || hasRemovedFiles -> draft.markPendingReview()
            }

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
        val uploadFileList: List<VideoFileInfo>? = null,
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
