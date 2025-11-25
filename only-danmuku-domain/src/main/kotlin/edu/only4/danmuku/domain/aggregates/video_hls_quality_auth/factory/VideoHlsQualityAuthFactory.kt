package edu.only4.danmuku.domain.aggregates.video_hls_quality_auth.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video_hls_quality_auth.VideoHlsQualityAuth

import org.springframework.stereotype.Service

/**
 * 视频清晰度授权策略
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/25
 */
@Service
@Aggregate(
    aggregate = "VideoHlsQualityAuth",
    name = "VideoHlsQualityAuthFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class VideoHlsQualityAuthFactory : AggregateFactory<VideoHlsQualityAuthFactory.Payload, VideoHlsQualityAuth> {

    override fun create(payload: Payload): VideoHlsQualityAuth {
        return VideoHlsQualityAuth().apply {

        }
    }

     @Aggregate(
        aggregate = "VideoHlsQualityAuth",
        name = "VideoHlsQualityAuthPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val name: String
    ) : AggregatePayload<VideoHlsQualityAuth>

}
