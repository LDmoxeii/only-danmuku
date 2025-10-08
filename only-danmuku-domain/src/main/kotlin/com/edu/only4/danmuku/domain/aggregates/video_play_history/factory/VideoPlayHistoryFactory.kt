package com.edu.only4.danmuku.domain.aggregates.video_play_history.factory

import com.edu.only4.danmuku.domain.aggregates.video_play_history.VideoPlayHistory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import org.springframework.stereotype.Service

@Service
@Aggregate(aggregate = "VideoPlayHistory", name = "VideoPlayHistoryFactory", type = Aggregate.TYPE_FACTORY, description = "")
class VideoPlayHistoryFactory : AggregateFactory<VideoPlayHistoryFactory.Payload, VideoPlayHistory> {
    override fun create(entityPayload: Payload): VideoPlayHistory {
        return VideoPlayHistory(

        )
    }

    @Aggregate(aggregate = "VideoPlayHistory", name = "VideoPlayHistoryPayload", type = Aggregate.TYPE_FACTORY_PAYLOAD, description = "")
    class Payload(

    ) : AggregatePayload<VideoPlayHistory>
}
