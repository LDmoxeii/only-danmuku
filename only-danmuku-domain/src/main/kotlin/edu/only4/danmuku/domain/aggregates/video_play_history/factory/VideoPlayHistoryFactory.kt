package edu.only4.danmuku.domain.aggregates.video_play_history.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video_play_history.VideoPlayHistory

import org.springframework.stereotype.Service

/**
 * 视频播放历史;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
@Aggregate(
    aggregate = "VideoPlayHistory",
    name = "VideoPlayHistoryFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class VideoPlayHistoryFactory : AggregateFactory<VideoPlayHistoryFactory.Payload, VideoPlayHistory> {

    override fun create(entityPayload: Payload): VideoPlayHistory {
        return VideoPlayHistory(
            id = 0L,
            customerId = entityPayload.customerId,
            videoId = entityPayload.videoId,
            fileIndex = entityPayload.fileIndex,
            createUserId = null,
            createBy = null,
            createTime = entityPayload.createTime,
            updateUserId = null,
            updateBy = null,
            updateTime = entityPayload.updateTime ?: entityPayload.createTime,
            deleted = 0L,
        )
    }

     @Aggregate(
        aggregate = "VideoPlayHistory",
        name = "VideoPlayHistoryPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val customerId: Long,
        val videoId: Long,
        val fileIndex: Int,
        val createTime: Long,
        val updateTime: Long? = null,
    ) : AggregatePayload<VideoPlayHistory>

}
