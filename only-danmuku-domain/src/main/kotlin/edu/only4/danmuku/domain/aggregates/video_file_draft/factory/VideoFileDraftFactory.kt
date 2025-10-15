package edu.only4.danmuku.domain.aggregates.video_file_draft.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video_file_draft.VideoFileDraft

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
    aggregate = "VideoFileDraft",
    name = "VideoFileDraftFactory",
    type = Aggregate.TYPE_FACTORY,
    description = ""
)
class VideoFileDraftFactory : AggregateFactory<VideoFileDraftFactory.Payload, VideoFileDraft> {

    override fun create(payload: Payload): VideoFileDraft {
        return VideoFileDraft(

        )
    }

     @Aggregate(
        aggregate = "VideoFileDraft",
        name = "VideoFileDraftPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val name: String
    ) : AggregatePayload<VideoFileDraft>

}
