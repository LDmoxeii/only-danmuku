package com.edu.only4.danmuku.domain.aggregates.video_file_draft

import com.edu.only4.danmuku.domain.aggregates.video_file_draft.factory.VideoFileDraftFactory
import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

class AggVideoFileDraft (
payload: VideoFileDraftFactory.Payload? = null,
) : Aggregate.Default<VideoFileDraft>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) : com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggVideoFileDraft, Long > (key)
}
