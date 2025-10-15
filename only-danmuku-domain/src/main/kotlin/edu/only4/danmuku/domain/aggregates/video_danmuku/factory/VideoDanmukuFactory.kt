package edu.only4.danmuku.domain.aggregates.video_danmuku.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video_danmuku.VideoDanmuku

import org.springframework.stereotype.Service

/**
 * 视频弹幕;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/10/15
 */
@Service
@Aggregate(
    aggregate = "VideoDanmuku",
    name = "VideoDanmukuFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class VideoDanmukuFactory : AggregateFactory<VideoDanmukuFactory.Payload, VideoDanmuku> {

    override fun create(payload: Payload): VideoDanmuku {
        return VideoDanmuku(

        )
    }

     @Aggregate(
        aggregate = "VideoDanmuku",
        name = "VideoDanmukuPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val name: String
    ) : AggregatePayload<VideoDanmuku>

}
