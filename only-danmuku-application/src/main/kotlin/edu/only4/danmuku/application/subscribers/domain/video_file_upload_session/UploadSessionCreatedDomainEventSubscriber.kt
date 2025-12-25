package edu.only4.danmuku.application.subscribers.domain.video_file_upload_session

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video_file_upload_session.InitTempAndStartUploadingCmd
import edu.only4.danmuku.application.distributed.clients.file_upload_session.CreateUploadSessionTempDirCli
import edu.only4.danmuku.domain.aggregates.video_file_upload_session.events.UploadSessionCreatedDomainEvent

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 上传上下文已创建
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/11/01
 */
@Service
class UploadSessionCreatedDomainEventSubscriber {

    @EventListener(UploadSessionCreatedDomainEvent::class)
    fun on(event: UploadSessionCreatedDomainEvent) {
        // 会话创建成功后，触发初始化临时目录并标记开始上传的命令
        val tempPath = Mediator.requests.send(
            CreateUploadSessionTempDirCli.Request(
                uploadId = event.entity.id
            )
        ).tempPath
        Mediator.commands.send(
            InitTempAndStartUploadingCmd.Request(
                uploadId = event.entity.id,
                tempPath = tempPath
            )
        )
    }
}
