package edu.only4.danmuku.domain.aggregates.video_draft.factory

import com.only4.cap4k.ddd.core.domain.aggregate.AggregateFactory
import com.only4.cap4k.ddd.core.domain.aggregate.AggregatePayload
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate

import edu.only4.danmuku.domain.aggregates.video_draft.VideoDraft
import edu.only4.danmuku.domain.aggregates.video.enums.PostType
import edu.only4.danmuku.domain.aggregates.video_draft.enums.VideoStatus

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

    override fun create(entityPayload: Payload): VideoDraft {
        return VideoDraft(
            videoCover = entityPayload.videoCover ?: "",
            videoName = entityPayload.videoName,
            customerId = entityPayload.customerId,
            pCategoryId = entityPayload.pCategoryId,
            categoryId = entityPayload.categoryId,
            status = entityPayload.status ?: VideoStatus.TRANSCODING,
            postType = entityPayload.postType ?: PostType.valueOf(1),
            originInfo = entityPayload.originInfo,
            tags = entityPayload.tags,
            introduction = entityPayload.introduction,
            interaction = null,
            duration = null,
            createUserId = null,
            createBy = null,
            createTime = null,
            updateUserId = null,
            updateBy = null,
            updateTime = null,
            deleted = 0L,
        )
    }

     @Aggregate(
        aggregate = "VideoDraft",
        name = "VideoDraftPayload",
        type = Aggregate.TYPE_FACTORY_PAYLOAD,
        description = ""
    )
    data class Payload(
        val videoId: Long,
        val customerId: Long,
        val videoName: String,
        val videoCover: String? = null,
        val pCategoryId: Long,
        val categoryId: Long? = null,
        val postType: PostType? = null,
        val originInfo: String? = null,
        val tags: String? = null,
        val introduction: String? = null,
        val status: VideoStatus? = null,
    ) : AggregatePayload<VideoDraft>

}
