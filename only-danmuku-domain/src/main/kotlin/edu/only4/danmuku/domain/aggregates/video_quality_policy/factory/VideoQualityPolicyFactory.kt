package edu.only4.danmuku.domain.aggregates.video_quality_policy.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video_quality_policy.VideoQualityPolicy

import org.springframework.stereotype.Service

/**
 * 视频清晰度策略;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2026/01/05
 */
@Service
@Aggregate(
    aggregate = "VideoQualityPolicy",
    name = "VideoQualityPolicyFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class VideoQualityPolicyFactory : AggregateFactory<VideoQualityPolicyFactory.Payload, VideoQualityPolicy> {

    override fun create(payload: Payload): VideoQualityPolicy {
        return VideoQualityPolicy().apply {

        }
    }

     @Aggregate(
        aggregate = "VideoQualityPolicy",
        name = "VideoQualityPolicyPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val name: String
    ) : AggregatePayload<VideoQualityPolicy>

}
