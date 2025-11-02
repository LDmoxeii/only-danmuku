package edu.only4.danmuku.application.subscribers.domain.video

import com.only4.cap4k.ddd.core.Mediator
import edu.only4.danmuku.application.commands.customer_profile.AdjustAuthorCoinAfterDeleteCmd
import edu.only4.danmuku.application.commands.file.DeleteVideoFileResourcesCmd
import edu.only4.danmuku.application.commands.video.DeleteVideoCmd
import edu.only4.danmuku.application.distributed.clients.DeleteVideoFileResourcesCli
import edu.only4.danmuku.domain.aggregates.video_post.events.VideoPostDeletedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class VideoPostDeletedDomainEventSubscriber {

    @EventListener(VideoPostDeletedDomainEvent::class)
    fun removeVideo(event: VideoPostDeletedDomainEvent) {
        val videoPost = event.entity
        Mediator.commands.send(
            DeleteVideoCmd.Request(
                videoPostId = videoPost.id,
            )
        )
    }

    @EventListener(VideoPostDeletedDomainEvent::class)
    fun adjustAuthorCoin(event: VideoPostDeletedDomainEvent) {
        val videoPost = event.entity
        Mediator.commands.send(
            AdjustAuthorCoinAfterDeleteCmd.Request(
                authorId = videoPost.customerId,
            )
        )
    }

    @EventListener(VideoPostDeletedDomainEvent::class)
    fun deletePhysicalFiles(event: VideoPostDeletedDomainEvent) {
        val video = event.entity
        Mediator.requests.send(
            DeleteVideoFileResourcesCli.Request(
                videoId = video.id,
                ownerId = video.customerId,
            )
        )
    }
}

