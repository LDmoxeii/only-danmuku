package edu.only4.danmuku.domain.aggregates.video_danmuku.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video_danmuku.VideoDanmuku

import org.springframework.stereotype.Service

/**
 * 视频弹幕;
 */
@Service
@Aggregate(
    aggregate = "VideoDanmuku",
    name = "VideoDanmukuFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class VideoDanmukuFactory : AggregateFactory<VideoDanmukuFactory.Payload, VideoDanmuku> {

    override fun create(entityPayload: Payload): VideoDanmuku {
        return VideoDanmuku(
            id = 0L,
            videoId = entityPayload.videoId,
            fileId = entityPayload.fileId,
            customerId = entityPayload.customerId,
            postTime = entityPayload.postTime,
            text = entityPayload.text,
            mode = entityPayload.mode,
            color = entityPayload.color,
            time = entityPayload.time,
            createUserId = null,
            createBy = null,
            createTime = null,
            updateUserId = null,
            updateBy = null,
            updateTime = null,
            deleted = 0L,
        )
    }

     @Aggregate(
        aggregate = "VideoDanmuku",
        name = "VideoDanmukuPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
         val videoId: Long,
         val fileId: Long,
         val customerId: Long,
         val postTime: Long,
         val text: String?,
         val mode: Boolean?,
         val color: String?,
         val time: Int?,
    ) : AggregatePayload<VideoDanmuku>

}
