package com.edu.only4.danmuku.domain.aggregates.video_draft

import com.edu.only4.danmuku.domain.aggregates.video_draft.factory.VideoDraftFactory
import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

class AggVideoDraft (
payload: VideoDraftFactory.Payload? = null,
) : Aggregate.Default<VideoDraft>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) : com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggVideoDraft, Long > (key)
}
