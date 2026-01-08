package edu.only4.danmuku.application.subscribers.domain.video_hls_key_token

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video.ApplyVideoPlayCountDeltaCmd
import edu.only4.danmuku.application.commands.video_play_history.AddPlayHistoryCmd
import edu.only4.danmuku.domain.aggregates.video_hls_key_token.events.VideoHlsKeyTokenIssuedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 视频 HLS 密钥 Token 已签发
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2026/01/08
 */
@Service
class VideoHlsKeyTokenIssuedDomainEventSubscriber {

    @EventListener(VideoHlsKeyTokenIssuedDomainEvent::class)
    fun applyVideoPlayCountDelta(event: VideoHlsKeyTokenIssuedDomainEvent) {
        Mediator.cmd.send(ApplyVideoPlayCountDeltaCmd.Request(
            videoId = event.entity.videoId!!,
            delta = 1
        ))
    }

    @EventListener(VideoHlsKeyTokenIssuedDomainEvent::class)
    fun addPlayHistoryCmd(event: VideoHlsKeyTokenIssuedDomainEvent) {
        val customerId = event.entity.createUserId!!
        Mediator.cmd.send(AddPlayHistoryCmd.Request(
            customerId = customerId,
            videoId = event.entity.videoId!!,
            fileIndex = event.entity.fileIndex
        ))
    }
}
