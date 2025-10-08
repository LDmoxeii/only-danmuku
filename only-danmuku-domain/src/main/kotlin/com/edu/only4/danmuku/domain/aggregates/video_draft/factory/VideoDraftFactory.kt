package com.edu.only4.danmuku.domain.aggregates.video_draft.factory

import com.edu.only4.danmuku.domain.aggregates.video_draft.VideoDraft
import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import org.springframework.stereotype.Service

@Service
@Aggregate(aggregate = "VideoDraft", name = "VideoDraftFactory", type = Aggregate.TYPE_FACTORY, description = "")
class VideoDraftFactory : AggregateFactory<VideoDraftFactory.Payload, VideoDraft> {
    override fun create(entityPayload: Payload): $ {
        Entity
    }

    {
        return VideoDraft(

        )
    }

    @Aggregate(aggregate = "VideoDraft", name = "VideoDraftPayload", type = Aggregate.TYPE_FACTORY_PAYLOAD, description = "")
    class Payload(

    ) : AggregatePayload<VideoDraft>
}
