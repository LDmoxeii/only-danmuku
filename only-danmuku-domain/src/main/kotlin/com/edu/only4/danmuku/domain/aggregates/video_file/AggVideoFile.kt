package com.edu.only4.danmuku.domain.aggregates.video_file

import com.edu.only4.danmuku.domain.aggregates.video_file.factory.VideoFileFactory
import com.only4.cap4k.ddd.core.domain.aggregate.Aggregate

class AggVideoFile (
payload: VideoFileFactory.Payload? = null,
) : Aggregate.Default<VideoFile>(payload) {

    val id by lazy { root.id }

    class Id(key: Long) : com.only4.cap4k.ddd.core.domain.aggregate.Id.Default<AggVideoFile, Long > (key)
}
