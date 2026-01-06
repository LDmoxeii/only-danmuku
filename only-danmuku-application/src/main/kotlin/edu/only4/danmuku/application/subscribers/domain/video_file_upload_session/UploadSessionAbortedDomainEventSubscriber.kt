package edu.only4.danmuku.application.subscribers.domain.video_file_upload_session

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.distributed.clients.file_upload_session.DeleteUploadSessionTempDirCli
import edu.only4.danmuku.domain.aggregates.video_file_upload_session.events.UploadSessionAbortedDomainEvent
import org.slf4j.LoggerFactory

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 视频文件上传会话已中止
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/19
 */
@Service
class UploadSessionAbortedDomainEventSubscriber {

    private val logger = LoggerFactory.getLogger(javaClass)

    @EventListener(UploadSessionAbortedDomainEvent::class)
    fun on(event: UploadSessionAbortedDomainEvent) {
        event.entity.tempDir?.takeIf { it.isNotBlank() }?.let { tempPath ->
            runCatching {
                Mediator.requests.send(
                    DeleteUploadSessionTempDirCli.Request(tempPath = tempPath)
                )
            }.onFailure { ex ->
                logger.warn("清理临时目录失败: {}", tempPath, ex)
            }
        }
    }
}
