package edu.only4.danmuku.application.subscribers.domain.video_file_post

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.domain.aggregates.video_file_post.events.VideoFilePostCreatedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import edu.only4.danmuku.application.distributed.clients.TranscodeVideoFileCli
import edu.only4.danmuku.application.commands.video_file_post.UpdateVideoFilePostTranscodeResultCmd

/**
 * 文件分P创建完成事件，驱动转码 CLI
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@Service
class VideoFilePostCreatedDomainEventSubscriber {

    @EventListener(VideoFilePostCreatedDomainEvent::class)
    fun on(event: VideoFilePostCreatedDomainEvent) {
        val file = event.entity
        val cliResult = Mediator.requests.send(
            TranscodeVideoFileCli.Request(
                videoFilePostId = file.id,
                uploadId = file.uploadId,
                customerId = file.customerId,
                videoId = file.videoId,
                fileIndex = file.fileIndex,
                fileName = file.fileName,
                tempPath = null
            )
        )

        Mediator.commands.send(
            UpdateVideoFilePostTranscodeResultCmd.Request(
                videoFilePostId = file.id,
                success = cliResult.success,
                outputPath = cliResult.outputPath,
                duration = cliResult.duration,
                fileSize = cliResult.fileSize,
                failReason = cliResult.failReason
            )
        )
    }
}
