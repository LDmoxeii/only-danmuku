package com.edu.only4.danmuku.domain.aggregates.video_play_history

import com.edu.only4.danmuku.domain.aggregates.video_play_history.factory.VideoPlayHistoryFactory
import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

class AggVideoPlayHistory (
payload: VideoPlayHistoryFactory.Payload? = null,
) : Aggregate.Default<VideoPlayHistory>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) : com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggVideoPlayHistory, Long > (key)
}
