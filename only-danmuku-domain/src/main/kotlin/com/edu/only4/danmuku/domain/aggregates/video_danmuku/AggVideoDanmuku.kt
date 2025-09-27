package com.edu.only4.danmuku.domain.aggregates.video_danmuku

import com.edu.only4.danmuku.domain.aggregates.video_danmuku.factory.VideoDanmukuFactory
import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

class AggVideoDanmuku (
payload: VideoDanmukuFactory.Payload? = null,
) : Aggregate.Default<VideoDanmuku>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) : com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggVideoDanmuku, Long > (key)
}
