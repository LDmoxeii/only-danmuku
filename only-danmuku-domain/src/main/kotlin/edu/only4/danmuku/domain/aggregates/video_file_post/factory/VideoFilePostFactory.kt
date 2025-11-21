package edu.only4.danmuku.domain.aggregates.video_file_post.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video_file_post.VideoFilePost

import org.springframework.stereotype.Service

/**
 * 视频文件信息;
 *
 * 本文件由[cap4k-ddd-codegen-gradle-plugin]生成
 * @author cap4k-ddd-codegen
 * @date 2025/11/21
 */
@Service
@Aggregate(
    aggregate = "VideoFilePost",
    name = "VideoFilePostFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class VideoFilePostFactory : AggregateFactory<VideoFilePostFactory.Payload, VideoFilePost> {

    override fun create(payload: Payload): VideoFilePost {
        return VideoFilePost().apply {

        }
    }

     @Aggregate(
        aggregate = "VideoFilePost",
        name = "VideoFilePostPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val name: String
    ) : AggregatePayload<VideoFilePost>

}
