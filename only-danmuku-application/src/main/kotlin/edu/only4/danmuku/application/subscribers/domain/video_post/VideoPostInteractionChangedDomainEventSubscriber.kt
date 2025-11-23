package edu.only4.danmuku.application.subscribers.domain.video_post

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.video.ChangeVideoInteractionCmd
import edu.only4.danmuku.domain.aggregates.video_post.events.VideoPostInteractionChangedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 视频互动设置已变更
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * 警告：可以在本文件中添加自定义事件处理方法
 * @author cap4k-ddd-codegen
 * @date 2025/10/21
 */
@Service
class VideoPostInteractionChangedDomainEventSubscriber {

    @EventListener(VideoPostInteractionChangedDomainEvent::class)
    fun on(event: VideoPostInteractionChangedDomainEvent) {
        val videoPost = event.entity
        Mediator.commands.send(
            ChangeVideoInteractionCmd.Request(
                videoPost.id,
                videoPost.customerId,
                videoPost.interaction
            )
        )
    }
}
