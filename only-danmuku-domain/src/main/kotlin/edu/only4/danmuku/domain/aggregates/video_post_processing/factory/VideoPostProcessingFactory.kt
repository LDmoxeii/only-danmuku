package edu.only4.danmuku.domain.aggregates.video_post_processing.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video_post_processing.VideoPostProcessing

import org.springframework.stereotype.Service

/**
 * 视频稿件处理聚合;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@Service
@Aggregate(
    aggregate = "VideoPostProcessing",
    name = "VideoPostProcessingFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class VideoPostProcessingFactory : AggregateFactory<VideoPostProcessingFactory.Payload, VideoPostProcessing> {

    override fun create(payload: Payload): VideoPostProcessing {
        return VideoPostProcessing().apply {

        }
    }

     @Aggregate(
        aggregate = "VideoPostProcessing",
        name = "VideoPostProcessingPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val name: String
    ) : AggregatePayload<VideoPostProcessing>

}
