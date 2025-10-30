package edu.only4.danmuku.domain.aggregates.video.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video.Video
import edu.only4.danmuku.domain.aggregates.video.VideoFile
import edu.only4.danmuku.domain.aggregates.video_post.VideoPost

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
        val post = entityPayload.videoPost
        return Video().apply {
            videoPostId = post.id
            customerId = post.customerId
            videoCover = post.videoCover
            videoName = post.videoName
            pCategoryId = post.pCategoryId
            categoryId = post.categoryId
            postType = post.postType
            originInfo = post.originInfo
            tags = post.tags
            introduction = post.introduction
            interaction = post.interaction
            duration = post.duration
            createUserId = post.createUserId
            createBy = post.createBy
            createTime = post.createTime
            updateUserId = post.updateUserId
            updateBy = post.updateBy
            updateTime = post.updateTime
            deleted = 0L

            videoFiles.clear()
            post.videoFilePosts
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
         val videoPost: VideoPost,
     ) : AggregatePayload<Video>

}
