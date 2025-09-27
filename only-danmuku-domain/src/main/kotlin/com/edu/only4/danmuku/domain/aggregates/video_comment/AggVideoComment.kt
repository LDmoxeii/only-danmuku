package com.edu.only4.danmuku.domain.aggregates.video_comment

import com.edu.only4.danmuku.domain.aggregates.video_comment.factory.VideoCommentFactory
import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

class AggVideoComment (
payload: VideoCommentFactory.Payload? = null,
) : Aggregate.Default<VideoComment>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) : com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggVideoComment, Long > (key)
}
