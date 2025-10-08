package com.edu.only4.danmuku.domain.aggregates.video_danmuku.factory

import com.edu.only4.danmuku.domain.aggregates.video_danmuku.VideoDanmuku
import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import org.springframework.stereotype.Service

@Service
@Aggregate(aggregate = "VideoDanmuku", name = "VideoDanmukuFactory", type = Aggregate.TYPE_FACTORY, description = "")
class VideoDanmukuFactory : AggregateFactory<VideoDanmukuFactory.Payload, VideoDanmuku> {
    override fun create(entityPayload: Payload): $ {
        Entity
    }

    {
        return VideoDanmuku(

        )
    }

    @Aggregate(aggregate = "VideoDanmuku", name = "VideoDanmukuPayload", type = Aggregate.TYPE_FACTORY_PAYLOAD, description = "")
    class Payload(

    ) : AggregatePayload<VideoDanmuku>
}
