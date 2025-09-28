package com.edu.only4.danmuku.domain.aggregates.video_play_history.specs

import com.edu.only4.danmuku.domain.aggregates.video_play_history.VideoPlayHistory
import com.only4.cap4k.ddd.core.domain.aggregate.Specification
import com.only4.cap4k.ddd.core.domain.aggregate.Specification.Result
import com.only4.cap4k.ddd.core.domain.aggregate.annotation.Aggregate
import org.springframework.stereotype.Service

/**
 *
 *
 * @author cap4k-ddd-codegen
 * @date 2025/09/27
 */
@Service
@Aggregate(aggregate = "VideoPlayHistory", name = "VideoPlayHistorySpecification", type = Aggregate.TYPE_SPECIFICATION, description = "")
class VideoPlayHistorySpecification : Specification<VideoPlayHistory> {

    override fun specify(entity: VideoPlayHistory): Result {
        return Result.pass()
    }

}
