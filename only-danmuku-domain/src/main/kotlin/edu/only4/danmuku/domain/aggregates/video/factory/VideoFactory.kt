package edu.only4.danmuku.domain.aggregates.video.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video.Video
import edu.only4.danmuku.domain.aggregates.video.VideoFile
import edu.only4.danmuku.domain.aggregates.video_draft.VideoDraft

import org.springframework.stereotype.Service

/**
 * 视频信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
@Aggregate(
    aggregate = "Video",
    name = "VideoFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class VideoFactory : AggregateFactory<VideoFactory.Payload, Video> {

    override fun create(entityPayload: Payload): Video {
        val draft = entityPayload.videoDraft
        return Video().apply {
            id = draft.id
            customerId = draft.customerId
            videoCover = draft.videoCover
            videoName = draft.videoName
            pCategoryId = draft.pCategoryId
            categoryId = draft.categoryId
            postType = draft.postType
            originInfo = draft.originInfo
            tags = draft.tags
            introduction = draft.introduction
            interaction = draft.interaction
            duration = draft.duration
            createUserId = draft.createUserId
            createBy = draft.createBy
            createTime = draft.createTime
            updateUserId = draft.updateUserId
            updateBy = draft.updateBy
            updateTime = draft.updateTime
            deleted = 0L

            videoFiles.clear()
            draft.videoFileDrafts
                .filter { it.isTransferSuccess() }
                .sortedBy { it.fileIndex }
                .forEach { fileDraft ->
                    val videoFile = VideoFile(
                        customerId = fileDraft.customerId,
                        fileName = fileDraft.fileName,
                        fileIndex = fileDraft.fileIndex,
                        fileSize = fileDraft.fileSize,
                        filePath = fileDraft.filePath,
                        duration = fileDraft.duration,
                        createUserId = fileDraft.createUserId,
                        createBy = fileDraft.createBy,
                        createTime = fileDraft.createTime,
                        updateUserId = fileDraft.updateUserId,
                        updateBy = fileDraft.updateBy,
                        updateTime = fileDraft.updateTime,
                        deleted = 0L,
                    )
                    videoFiles.add(videoFile)
                }
        }
    }

     @Aggregate(
        aggregate = "Video",
        name = "VideoPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
         val videoDraft: VideoDraft,
     ) : AggregatePayload<Video>

}
