package edu.only4.danmuku.application.subscribers.domain.video_post

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_post_processing.StartVideoPostProcessingCmd
import edu.only4.danmuku.domain.aggregates.video_post.events.VideoPostTranscodingRequestedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 视频稿件转码请求
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class VideoPostTranscodingRequestedDomainEventSubscriber {

    @EventListener(VideoPostTranscodingRequestedDomainEvent::class)
    fun on(event: VideoPostTranscodingRequestedDomainEvent) {
        if (event.fileList.isEmpty()) {
            return
        }

        val fileList = event.fileList.map { file ->
            StartVideoPostProcessingCmd.VideoPostProcessingFileSpec(
                uploadId = file.uploadId,
                fileIndex = file.fileIndex,
                fileName = file.fileName,
                fileSize = file.fileSize,
                duration = file.duration
            )
        }

        Mediator.commands.send(
            StartVideoPostProcessingCmd.Request(
                videoPostId = event.videoPostId,
                fileList = fileList
            )
        )
    }
}
