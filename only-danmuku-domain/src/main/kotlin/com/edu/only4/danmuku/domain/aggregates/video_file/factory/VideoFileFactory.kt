package com.edu.only4.danmuku.domain.aggregates.video_file.factory

import com.edu.only4.danmuku.domain.aggregates.video_file.VideoFile
import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import org.springframework.stereotype.Service

@Service
@Aggregate(aggregate = "VideoFile", name = "VideoFileFactory", type = Aggregate.TYPE_FACTORY, description = "")
class VideoFileFactory : AggregateFactory<VideoFileFactory.Payload, VideoFile> {
    override fun create(payload: Payload): VideoFile {
        return VideoFile(

        )
    }

    @Aggregate(aggregate = "VideoFile", name = "VideoFilePayload", type = Aggregate.TYPE_FACTORY_PAYLOAD, description = "")
    class Payload(

    ) : AggregatePayload<VideoFile>
}
