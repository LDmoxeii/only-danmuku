package com.edu.only4.danmuku.domain.aggregates.video

import com.edu.only4.danmuku.domain.aggregates.video.factory.VideoFactory
import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

class AggVideo (
payload: VideoFactory.Payload? = null,
) : Aggregate.Default<Video>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) : com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggVideo, Long > (key)
}
