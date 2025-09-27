package com.edu.only4.danmuku.domain.aggregates.video_file_draft.factory

import com.edu.only4.danmuku.domain.aggregates.video_file_draft.VideoFileDraft
import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import org.springframework.stereotype.Service

@Service
@Aggregate(aggregate = "VideoFileDraft", name = "VideoFileDraftFactory", type = Aggregate.TYPE_FACTORY, description = "")
class VideoFileDraftFactory : AggregateFactory<VideoFileDraftFactory.Payload, VideoFileDraft> {
    override fun create(payload: Payload): VideoFileDraft {
        return VideoFileDraft(

        )
    }

    @Aggregate(aggregate = "VideoFileDraft", name = "VideoFileDraftPayload", type = Aggregate.TYPE_FACTORY_PAYLOAD, description = "")
    class Payload(

    ) : AggregatePayload<VideoFileDraft>
}
