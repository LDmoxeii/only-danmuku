package edu.only4.danmuku.domain.aggregates.video_file.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video_file.VideoFile

import org.springframework.stereotype.Service

/**
 * 视频文件信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
@Aggregate(
    aggregate = "VideoFile",
    name = "VideoFileFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class VideoFileFactory : AggregateFactory<VideoFileFactory.Payload, VideoFile> {

    override fun create(payload: Payload): VideoFile {
        return VideoFile(

        )
    }

     @Aggregate(
        aggregate = "VideoFile",
        name = "VideoFilePayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val name: String
    ) : AggregatePayload<VideoFile>

}
