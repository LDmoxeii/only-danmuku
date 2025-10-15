package edu.only4.danmuku.domain.aggregates.video_draft.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video_draft.VideoDraft

import org.springframework.stereotype.Service

/**
 * 视频信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
@Aggregate(
    aggregate = "VideoDraft",
    name = "VideoDraftFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class VideoDraftFactory : AggregateFactory<VideoDraftFactory.Payload, VideoDraft> {

    override fun create(payload: Payload): VideoDraft {
        return VideoDraft(

        )
    }

     @Aggregate(
        aggregate = "VideoDraft",
        name = "VideoDraftPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val name: String
    ) : AggregatePayload<VideoDraft>

}
